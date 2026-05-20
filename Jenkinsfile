pipeline {

    agent any

    stages {

        stage('Build') {
            steps {

                /*
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
                */

                echo("Dev Build")
            }

            /*
            post {
                success {
                    junit 'target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
            */
        }

        stage('Deploy to QA') {
            steps {
                echo("Deploy to QA done")
            }
        }

        stage('Regression Automation Tests') {
            steps {

                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {

                    /*
                    git 'https://github.com/naveenanimation20/Dec2024OpenCartFramework.git'

                    sh """
                    mvn clean test \
                    -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml \
                    -Denv=qa
                    """
                    */

                    echo("Regression Automation Tests")
                }
            }
        }

        /*
        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }

        stage('Publish ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }
        */

        stage('Deploy to Stage') {
            steps {
                echo("Deploy to Stage")
            }
        }

        stage('Sanity Automation Test') {
            steps {

                /*
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {

                    git 'https://github.com/naveenanimation20/Dec2024OpenCartFramework.git'

                    sh """
                    mvn clean test \
                    -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml \
                    -Denv=stage
                    """
                }
                */

                echo("Sanity Automation Test")
            }
        }

        /*
        stage('Publish Sanity ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML Sanity ChainTest Report',
                    reportTitles: ''
                ])
            }
        }
        */

        stage('Deploy to PROD') {
            steps {
                echo("Deploy to PROD")
            }
        }
    }
}