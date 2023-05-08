def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Compile") {

            def conf = "app/conf.txt"
            props = readProperties file : conf

            switch (props.compileMethod) {
                case "mvn":
                    println "mvn"
                    break
                default:
                    println "default"
                    break
            }
        }
    }
}