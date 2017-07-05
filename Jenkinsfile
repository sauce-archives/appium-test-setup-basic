#!groovy

pipeline {
    agent {
        docker "maven:3.3.9"
    }

    stages {
        stage("staging test") {
            when {
                expression { params.APPIUM_SERVER == 'http://appium.staging.testobject.org/wd/hub' }
            }
            steps {
                lock (resource: params.TESTOBJECT_DEVICE_ID) {
                    sh "mvn -B clean test"
                }
            }
        }
        stage("test") {
            when {
                 expression { params.APPIUM_SERVER != 'http://appium.staging.testobject.org/wd/hub' }
            }
            steps {
                sh "mvn -B clean test"
            }
        }
    }

    post {
        always {
            junit "target/surefire-reports/*.xml"
        }
        failure {
            script {
                if (params.APPIUM_SERVER == 'http://appium.testobject.com/wd/hub') {
                    slackSend channel: "#${env.SLACK_CHANNEL}", color: "bad", message: "`${env.JOB_BASE_NAME} failed (<${BUILD_URL}|open>)", teamDomain: "${env.SLACK_SUBDOMAIN}", token: "${env.SLACK_TOKEN}"
                }
            }
        }
    }
}
