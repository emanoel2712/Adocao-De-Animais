pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
       
 
    stage('Results'){
      steps{
        script{
          def logz = currentBuild.rawBuild.getLog(1000);
          def result = logz.Find{it.contains('FAIL')}
          if(result){
            error('FAILING TO DUE' + result)    
          }
       }
     }
   }
}
     post {
         always {
            echo 'Jenkins - Email enviado apos o build'          
              emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
        }
    }
}
