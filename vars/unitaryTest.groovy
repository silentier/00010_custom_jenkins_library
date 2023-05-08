def call ( Map popertyInfo ){

    agent {
        docker {
            image 'node:14-alpine'
            label 'docker-node'
        }
    }

        stage ("unitaryTest") {
            checkout scm

            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
            println "compileMethod:"+props.compileMethod

            switch (props.testMethod) {
                case "mvn":
                    sh ("mvn "+props.testMethod)
                    break
                default:
                    println "default"
                    break
            }
        }

}