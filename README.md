# Shared jenkins function library for Six docker base image builds

## Documentation
https://jenkins.io/doc/book/pipeline/shared-libraries/


## Usage
### to include the full function set from:
### [jenkins-shared-library git repo](https://stash.six-group.net/projects/SDBI/repos/jenkins-shared-library/browse?at=refs%2Fheads%2Ffeature%2Fimproved_documentation)
```
library identifier: 'sdbi-shared-lib@develop', retriever: modernSCM(
        [$class       : 'GitSCMSource',
         remote       : 'ssh://git@stash.six-group.net:22/sdbi/jenkins-shared-library.git',
         credentialsId: 'six-baseimages-bitbucket-secret'])
```
### to run a function
```
defaultProperties()
```
or:
```
jobContext.registry = getImageStreamRegistryUrl('six-rhel7')
```
(the name of the file in the jenkins-shared-library is the name of the function)
