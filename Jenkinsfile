pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Jafestro/Inventory-Management-Sys.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean install'
                    } else {
                        bat 'mvn clean install'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }

        stage('Code Coverage') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn jacoco:report'
                    } else {
                        bat 'mvn jacoco:report'
                    }
                }
                jacoco execPattern: '**/target/jacoco.exec'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            jacoco execPattern: '**/target/jacoco.exec'
        }
    }
}
