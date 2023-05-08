def call ( Map popertyInfo ){
    node ("master") {
        stage ("Check File") {
            def conf : String = "app/conf.txt"
            props = readProperties file : conf

            prinln pros
        }
    }
}