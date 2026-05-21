pipeline 
{
    agent any
    
    tools{
        maven 'Maven'
        }

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 /*git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"*/
                 echo("Dev Repo")
            }
            post 
            {
                success
                {
                    /*junit '*target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target.jar'*/
                    echo("Dev Test Results")

                }
            }
        }
        
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }
        
        
        
                
        stage('Regression Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
				git branch: 'main', url: 'https://github.com/Roopavh/OpenCartFramework.git'   
				bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/testng_chrome.xml -Denv=qa"
                    
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
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        
        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }
        
        stage('Sanity Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git branch: 'main', url: 'https://github.com/Roopavh/OpenCartFramework.git'
                    bat "mvn clean test -DsuiteXmlFile=src/test/resources/testrunners/testng_chrome.xml -Denv=stage"
                    
                }
            }
        }
        
        
        
        stage('Publish sanity ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML Sanity ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }
        
        
    }
}