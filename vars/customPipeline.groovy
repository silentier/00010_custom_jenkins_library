def call ( Map popertyInfo ){
    agent {
        docker {
            image 'maven:3.9.0-eclipse-temurin-11'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stage ("In Docker") {
        def conf = "app/conf.txt"
        props = readProperties file : conf

        println props
    }

}