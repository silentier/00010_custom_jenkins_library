def call ( Map popertyInfo ){

    node {
        checkout scm

        docker.withRegistry('https://registry.example.com') {

            docker.image('my-custom-image').inside {
                sh 'make test'
            }
        }
    }
        stage ("custom Stages") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
        }


}