pipeline {

    agent any

    tools {
        maven 'maven'
    }

    options {
        timestamps()
    }
    stages {
        //=========================================================
        // BUILD SAMPLE PROJECT
        //=========================================================
        stage('Build Sample Project') {
            steps {

                dir('SampleProject') {

                    git branch: 'master',
                        url: 'https://github.com/jglick/simple-maven-project-with-tests.git'

                    bat 'mvn clean test'

                }

            }

            post {

                always {

                    junit 'SampleProject/target/surefire-reports/TEST-*.xml'

                    archiveArtifacts artifacts: 'SampleProject/target/*.jar',
                                     fingerprint: true

                }

            }

        }

        //=========================================================
        // BUILD OPENCART FRAMEWORK
        //=========================================================

        stage('Checkout OpenCart Framework') {

            steps {

                dir('OpenCartFramework') {

                    git branch: 'main',
                        url: 'https://github.com/Roopavh/OpenCartFramework.git'
                }
            }
        }

        //=========================================================
        // DEPLOY QA
        //=========================================================

        stage('Deploy to QA') {

            steps {

                echo "Deploying application to QA..."

            }
        }

        //=========================================================
        // REGRESSION
        //=========================================================

        stage('Regression Automation Tests') {

            steps {

                dir('OpenCartFramework') {

                    catchError(buildResult: 'SUCCESS',
                               stageResult: 'FAILURE') {

                        bat '''
                        mvn clean test ^
                        -DsuiteXmlFile=src/test/resources/testrunners/testng_regression.xml ^
                        -Denv=qa
                        '''

                    }
                }
            }

            post {

                always {

                    junit allowEmptyResults: true,
                          testResults: 'OpenCartFramework/target/surefire-reports/*.xml'

                }

            }

        }

        //=========================================================
        // ALLURE REPORT
        //=========================================================

        stage('Publish Allure Report') {

            steps {

                allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'OpenCartFramework/allure-results']]
                ])

            }

        }

        //=========================================================
        // CHAINTEST REPORT
        //=========================================================

        stage('Publish Regression ChainTest Report') {

            steps {

                publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'OpenCartFramework/target/chaintest',
                        reportFiles: 'Index.html',
                        reportName: 'Regression ChainTest Report'
                ])

            }

        }

        //=========================================================
        // DEPLOY STAGE
        //=========================================================

        stage('Deploy to Stage') {

            steps {

                echo "Deploying application to Stage..."

            }

        }

        //=========================================================
        // SANITY
        //=========================================================

        stage('Sanity Automation Tests') {

            steps {

                dir('OpenCartFramework') {

                    catchError(buildResult: 'SUCCESS',
                               stageResult: 'FAILURE') {

                        bat '''
                        mvn clean test ^
                        -DsuiteXmlFile=src/test/resources/testrunners/testng_sanity.xml ^
                        -Denv=stage
                        '''

                    }

                }

            }

            post {

                always {

                    junit allowEmptyResults: true,
                          testResults: 'OpenCartFramework/target/surefire-reports/*.xml'

                }

            }

        }

        //=========================================================
        // SANITY CHAINTEST
        //=========================================================

        stage('Publish Sanity ChainTest Report') {

            steps {

                publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'OpenCartFramework/target/chaintest',
                        reportFiles: 'Index.html',
                        reportName: 'Sanity ChainTest Report'
                ])

            }

        }

        //=========================================================
        // DEPLOY PROD
        //=========================================================

        stage('Deploy to Production') {

            steps {

                echo "Deploying application to Production..."

            }

        }

    }

    post {
        always {
            cleanWs()

        }

        success {

            echo "Pipeline completed successfully."

        }

        failure {

            echo "Pipeline completed with failures. Please review the reports."
        }

    }

}