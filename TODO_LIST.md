### TODO List
Use a branch for each item in the list
* 1 - Make toStrings match what is shown in the sample app <Done>
* 2 - Create custom Exceptions for all important issues <Done>
    Getting News by id:
    * ERROR_CODE: 000010 ERROR_MESSAGE: News id can not be null or less than 1. News id is: -1
    * ERROR_CODE: 000001 ERROR_MESSAGE: News with id 123 does not exist.
    * ERROR_CODE: 000013 ERROR_MESSAGE: Author Id should be number
    Creating newsModel:
    * ERROR_CODE: 000013 ERROR_MESSAGE: Author Id should be number
    * ERROR_CODE: 000010 ERROR_MESSAGE: Author id can not be null or less than 1. Author id is: -1
    * ERROR_CODE: 000002 ERROR_MESSAGE: Author Id does not exist. Author Id is: 123
    * ERROR_CODE: 000012 ERROR_MESSAGE: News title can not be less than 5 and more than 30 symbols. News title is a
    * ERROR_CODE: 000012 ERROR_MESSAGE: News content can not be less than 5 and more than 255 symbols. News content is a
    Updating newsModel:
    * ERROR_CODE: 000013 ERROR_MESSAGE: News Id should be number    
    * ERROR_CODE: 000010 ERROR_MESSAGE: News id can not be null or less than 1. News id is: -1
    * ERROR_CODE: 000013 ERROR_MESSAGE: Author Id should be number
    * ERROR_CODE: 000010 ERROR_MESSAGE: Author id can not be null or less than 1. Author id is: -1
    * ERROR_CODE: 000012 ERROR_MESSAGE: News title can not be less than 5 and more than 30 symbols. News title is a
    * ERROR_CODE: 000012 ERROR_MESSAGE: News content can not be less than 5 and more than 255 symbols. News content is a
    Removing newsModel:
    * ERROR_CODE: 000013 ERROR_MESSAGE: News Id should be number
    * ERROR_CODE: 000010 ERROR_MESSAGE: News id can not be null or less than 1. News id is: -1

* 3 - Create interfaces for all class categories (decoupling) <Done>
* 4 - Create a new data source that match the data files I was Given <Done>
* 5 - Move all literals to properties file <?>
* 6 - Inverting all equals <Done>
* 8 - Testing of Services and more <Done?>
* 9 - Start submitting and fixing bugs.