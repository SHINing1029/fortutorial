node('remote'){
  stage 'Checkout Git project'
  git url:https://github.com/SHINing1029/fortutorial.git
  def appVersion = version()
  if (appVersion) {
    echo "Building version ${appVersion}"
  }

  stage 'Build Maven project'
  def mvnHome = tool 'M3'
  sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore verify"
  step ([$class:'junitResultArchiver', testResults: '**/target/surefire-reports/Test-*.xml'])
}

def version(){
  def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
  matcher ? matcher[0][1] : null
}
