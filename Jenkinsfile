pipeline {
    agent any

    environment {
        // Provide DB credentials via Jenkins Credentials (add as secret text or string credentials)
        DB_URL = credentials('DB_URL')
        DB_USER = credentials('DB_USER')
        DB_PASSWORD = credentials('DB_PASSWORD')

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
            steps {
                // Runs on Jenkins agent host; ensure Maven is available on the node
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

        stage('Archive artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
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
