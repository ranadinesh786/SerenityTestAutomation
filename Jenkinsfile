pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build -Dcucumber.filter.tags="@smoke" test'
            }
        }
        stage('Publish Reports') {
            steps {
                junit '**/build/test-results/test/*.xml'
                cucumber buildStatus: 'UNSTABLE', fileIncludePattern: '**/cucumber-reports/*.json'
            }
        }
    }
}
