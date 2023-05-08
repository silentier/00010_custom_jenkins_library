def call ( Map popertyInfo ){
    node ("master") {
        stage ("Check File") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
        }
    }
}