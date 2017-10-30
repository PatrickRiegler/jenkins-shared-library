def dockerToken(String login = "serviceaccount") {
    node() {
        // Read the auth token from the file defined in the env variable AUTH_TOKEN
        String token = sh returnStdout: true, script: 'cat ${AUTH_TOKEN}'
        String prefix
        if (login) {
            prefix = "${login}:"
        } else {
            prefix = ''
        }
        return prefix + token
    }
}
