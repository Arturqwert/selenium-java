echo "rm allure-results"
rm -rf ./allure-results

echo "allure-test"
mvn -Dtest="PageFactory.LabirintTest" clean test

echo "allure-history"
cp -r ./allure-report/history ./allure-results/history

echo "rm allure-report"
rm -rf ./allure-report
echo "Browser=Chrome" >> ./allure-results/environment.properties
echo "Browser.Version=121" >> ./allure-results/environment.properties
echo "Stand=Prod" >> ./allure-results/environment.properties
echo "User=$(whoami)" >> ./allure-results/environment.properties
echo "Agent.OS.Name=Windows" >> ./allure-results/environment.properties
echo "Agent.OS.Version=10.0.22621" >> ./allure-results/environment.properties

echo "allure-generate"
allure generate --clean

echo "allure-open"
allure open