def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Check File") {

            def conf = "app/conf.txt"
            props = readProperties file : conf

            switch (props.compile.method) {
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