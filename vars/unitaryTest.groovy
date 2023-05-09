def call ( Map popertyInfo ){

    node("master") {
        stage("unitaryTest") {
            checkout scm

            def conf = "app/conf.txt"
            props = readProperties file: conf

            println props
            println "compileMethod:" + props.compileMethod

            sh("ls -la")

            switch (props.testMethod) {
                case "mvn":
                    sh("mvn " + props.testMethod)
                    break
                default:
                    println "default"
                    break
            }
        }
    }
}