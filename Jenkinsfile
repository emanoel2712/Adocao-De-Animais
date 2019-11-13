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

     
