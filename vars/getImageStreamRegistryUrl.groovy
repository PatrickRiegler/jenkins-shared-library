// vars/getImageStreamRegistryUrl.groovy
def call(String imageStreamName) {
    def url = sh returnStdout: true, script: "oc get is ${imageStreamName} --template='{{ .status.dockerImageRepository }}'"
    return url
}
