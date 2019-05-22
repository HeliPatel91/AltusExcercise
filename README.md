
Back-End Exercise

This exercise contains 3 parts that can be completed separately. Please take a note of how long you spend on this test.

1) Git
Start by uploading the project to a public repository, so that the link can be sent to us after completing the exercise. Try and let commits show your progress with the task, with appropriate messages.

2) Code analysis
The class XMLFileUnpackager.java converts XML files to CSV files following a specific mapping and doing a simple SUM calculation. (A sleep call was added in addValues(final String values) to simulate a heavy calculation.) The file submission/outputFile.csv shows you what is expected from converting submission/inputFile.xml

The method convertAndSave(File csvFile) in XMLFileUnpackager.java contains 2 bugs and 1 performance issue. Editing this one method ONLY, fix it so that the tests in ExerciseApplicationTests.compareGeneratedFiles() are passing. 

/!\ Do not modify any other code than this method!

3) Architecture
Write a simple Rest API in the same module that will expose this feature, taking an xml file as parameter and returning the converted CSV file.
