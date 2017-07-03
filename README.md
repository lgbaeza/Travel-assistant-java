# Node.js Hello World Sample

This application demonstrates a simple, reusable Node.js chatbot web application based on the Express framework.

## Configure watson conversation service
1. Import Watson conversation workspace (found at _watson folder)
2. Configure watson conversation. Replace "{your-workspace-id}" for your workspace ID in the file src/main/java/com/lbaeza/watson/watsonClient.java at line 72
2. Replace "{your-username}" and "{your-password}" for your conversation credentials in line indicadet above at lines 73 and 74

## Configure watson document conversion service
1. Replace "{your-username}" and "{your-password}" for your document conversion credentials in file indicaded above at line 46

## Configure API connect service
1. Import APIs with the yml files (found at _apis folder)
2. Add APIs to a catalog and get the Client ID.
3. Replace Client Id in the file src/main/java/com/lbaeza/watson/ApiClient.java at line 12
4. Replace "{WEATHER-api-endpoint}", with weather API endpoint at line 19
5. Replace "{GMAP-api-endpoint}", with gmap API endpoint at line 36
6. Replace "{WIKIPEDIA-api-endpoint}", with wikipedia API endpoint at line 59

## Publish the app
+ compile the app with Maven
+ Configure the name and host of your app in the "manifest.yml" file
+ Run "cf push"