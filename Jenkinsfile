pipeline {
    agent any

    environment {
        // Non-secret defaults (override in Jenkins job/agent as needed)
        DOCKER_IMAGE = ''
        DOCKER_REGISTRY = ''
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Unit Test') {
            agent {
                docker {
                    image 'maven:3.9.4-eclipse-temurin-21'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn -B -DskipTests=false test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build (package)') {
            agent {
                docker {
                    image 'maven:3.9.4-eclipse-temurin-21'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn -B -DskipTests package'
            }
        }

        stage('Start DB (optional)') {
            when {
                expression { return fileExists('docker-compose.yml') }
            }
            steps {
                script {
                    echo 'docker-compose.yml found — starting dependent services (Postgres)'
                    sh 'docker-compose up -d'
                    // wait for db to be ready (simple loop)
                    sh '''
                        n=0
                        until [ $n -ge 30 ]
                        do
                            docker-compose ps | grep -q "Up" && break || true
                            n=$((n+1))
                            sleep 2
                        done
                    '''
                }
            }
        }

        stage('Integration tests (optional)') {
            when {
                expression { return env.RUN_INTEGRATION == 'true' }
            }
            agent {
                docker {
                    image 'maven:3.9.4-eclipse-temurin-21'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                // Use Jenkins credentials to provide DB values securely.
                // Expected credential IDs (add them to Jenkins Credentials as secret text):
                // - DB_URL (secret text)
                // - DB_USER (secret text)
                // - DB_PASSWORD (secret text)
                withCredentials([
                    string(credentialsId: 'DB_URL', variable: 'DB_URL'),
                    string(credentialsId: 'DB_USER', variable: 'DB_USER'),
                    string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD')
                ]) {
                    sh 'mvn -B verify -Dspring.datasource.url="$DB_URL" -Dspring.datasource.username="$DB_USER" -Dspring.datasource.password="$DB_PASSWORD"'
                }
            }
            post {
                always {
                    junit '**/target/failsafe-reports/*.xml'
                }
            }
        }

        stage('Archive artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker image (optional)') {
            when {
                expression { return env.DOCKER_IMAGE?.trim() }
            }
            steps {
                script {
                    def tag = env.DOCKER_IMAGE
                    sh "docker build -t ${tag} ."
                    if (env.DOCKER_REGISTRY) {
                        sh "docker tag ${tag} ${env.DOCKER_REGISTRY}/${tag}"
                        sh "docker push ${env.DOCKER_REGISTRY}/${tag}"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                if (fileExists('docker-compose.yml')) {
                    sh 'docker-compose down || true'
                }
            }
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.log'
        }
        success {
            echo 'Build succeeded'
        }
        failure {
            echo 'Build failed — check logs.'
        }
    }
}
