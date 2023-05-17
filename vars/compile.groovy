def call ( Map popertyInfo ){

    node ("jdk17mvn") {

        stage ("Compile") {
            checkout scm

            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
            println "compileMethod:"+props.compileMethod

            switch (props.compileMethod) {
                case "mvn":
                    sh ("mvn "+props.compileCommand)
                    break
                default:
                    println "default"
                    break
            }
        }
    }
}