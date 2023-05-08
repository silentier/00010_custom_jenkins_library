def call ( Map popertyInfo ){

    agent {
        docker {
            image 'node:14-alpine'
            label 'docker-node'
        }
    }

      stage("Stages") {
          unitaryTest();
          compile();
      }

}