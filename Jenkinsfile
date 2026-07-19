pipeline 
{
    agent any
    
    tools {
        maven 'maven'
    }

    stages 
    {
        stage('Build') 
        {
            steps
            {
                dir('sample-project') {
                    git branch: 'master', url: 'https://github.com/jglick/simple-maven-project-with-tests.git'
                    bat "mvn -Dmaven.test.failure.ignore=true clean package"
                }
            }
            post 
            {
                success
                {
                    junit 'sample-project/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'sample-project/target/*.jar'
                }
            }
        }
        
        stage("Deploy to QA") {
            steps {
                echo "deploy to qa done"
            }
        }
        
        stage('Regression Automation Tests') {
            steps {
                dir('OpenCartFramework') {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        git branch: 'main', url: 'https://github.com/Roopavh/OpenCartFramework.git'
                        bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
                    } 
                }
            }
        }
     
        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'OpenCartFramework/allure-results']]
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
                    reportDir: 'OpenCartFramework/target/chaintest', 
                    reportFiles: 'Index.html', 
                    reportName: 'HTML Regression ChainTest Report', 
                    reportTitles: ''
                ])
            }
        }
        
        stage("Deploy to Stage") {
            steps {
                echo "deploy to Stage"
            }
        }
        
        stage('Sanity Automation Test') {
            steps {
                dir('OpenCartFramework') {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        // Explicitly checking out the 'main' branch
                        git branch: 'main', url: 'https://github.com/Roopavh/OpenCartFramework.git'
                        bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/testng_sanity.xml -Denv=stage"
                    }  
                }
            }
        }
        
        stage('Publish sanity ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false, 
                    keepAll: true, 
                    reportDir: 'OpenCartFramework/target/chaintest', 
                    reportFiles: 'Index.html', 
                    reportName: 'HTML Sanity ChainTest Report', 
                    reportTitles: ''
                ])
            }
        }
        
        stage("Deploy to PROD") {
            steps {
                echo "deploy to PROD"
            }
        }
    }
}