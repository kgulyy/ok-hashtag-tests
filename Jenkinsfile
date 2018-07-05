pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    environment {
        LOGIN           = 'technopark60'
        PASSWORD        = 'testQA1'
        WEBDRIVER_TYPE  = 'remote'
        WEBDRIVER_REMOTE_URL   = 'http://178.128.36.229:4444/wd/hub'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}
