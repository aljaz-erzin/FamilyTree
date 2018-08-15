                                            **** FAMILY-TREE ****
                                        
                                Application FamilyTree builds a family tree. 

Package contains:

    Main folder (java):

    - App.java -> main functions
    - Person.java -> Object for a family member (names are converted to numbers)
    - ResultTree.java -> Object for a result tree, with print and check functions
    - SortingAlgorithm -> Algorithm for sorting elements in a priority queue
    
    Test folder  (java):

    - AppTest.java -> Junit tests for crucial methods in App.java
    
    data.txt -> data for App.java
    
    test_data.txt -> data for AppTest.java

Run application:

    cd FamilyTree  
    mvn clean install
    mvn compile
    mvn exec:java -Dexec.mainClass=App;

Expected output (data.txt):

    Ivan
          Adam
                Stjepan
                      Marko
                      Robert
          Fran
    Luka
          Leopold
      
      
Expected output if test_data.txt is used in App.java:


    Maks
          Robert
                Aljaž
                Andraž
          Andreja
                Luka
                Urh
                Ana
    Ivan
          Urška
                Aljaž
                Andraž
          Milena
                Gašper
                Amadej

Run tests:

    mvn test