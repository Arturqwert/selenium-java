echo "allure-results"
rm -rf ./allure-results

echo "allure-test"
mvn -Dtest="PageFactory.LabirintTest" clean test

echo "allure-history"
cp -r ./allure-report/history ./allure-results/history

echo "allure-generate"
allure generate --clean

echo "allure-open"
allure open