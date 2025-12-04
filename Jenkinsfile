pipeline {
    agent any

    environment {
        // Jenkins Credential ID’leri yoksa geçici test değerleri
        DB_URL      = credentials('DB_URL')  // Jenkins'te bu credential tanımlı olmalı
        DB_USER     = credentials('DB_USER')
        DB_PASSWORD = credentials('DB_PASSWORD')
        DOCKER_IMAGE = ''       // opsiyonel, boş bırak
        DOCKER_REGISTRY = ''    // opsiyonel, boş bırak
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
                    // wait for db to be ready
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
            steps {
                sh 'mvn -B verify'
            }
            post {
                always {
                    junit '**/target/failsafe-reports/*.xml'
                }
            }
        }

        stage('Archive artifacts') {
            steps {
                node {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
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
            node {
                if (fileExists('docker-compose.yml')) {
                    sh 'docker-compose down || true'
                }
                archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.log'
            }
        }
        success {
            echo 'Build succeeded'
        }
        failure {
            echo 'Build failed — check logs.'
        }
    }
}
