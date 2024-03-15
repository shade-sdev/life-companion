pipeline {
    agent any

    tools {
        jdk 'openjdk'
        maven 'maven3'
    }

    stages {
        stage('Git Checkout') {
            steps {
                git 'https://github.com/shade-sdev/life-companion.git'
            }
        }

         stage('Compile') {
             steps {
                 sh "mvn clean compile"
             }
         }

         stage('Build') {
             steps {
                 sh "mvn clean install"
             }
         }

        stage('Build Docker Image') {
            steps {
                script {

                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-credentials-id') {
                        dockerfileLocation = "${WORKSPACE}/Dockerfile"
                        def customImage =  docker.build('life-companion-app:latest', '-f ${dockerfileLocation} .')

                        /* Push the container to the custom Registry */
                        customImage.push()
                    }
                }
            }
        }
    }
}
