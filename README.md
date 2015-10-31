# magnolia-command-api-example
An example of the Magnolia CMS Command API returning a detailed response.

For an example, build the module and install.  
**NB**: Read the [Magnolia documentation](https://documentation.magnolia-cms.com/display/DOCS/REST+API#RESTAPI-Enablingcommands "Enabling commands") on granting access to the Commands endpoint. This module will not enable access on installation.  

The Magnolia documentation is lacking information on how to obtain a response beyond a simple Boolean indicating success. This document explains how this is, in fact, supported via configuration.

When adding a command to the endpoint's list of enabled commands, an additional content node, contextParameters, can be added with a list of strings naming parameters that should be collected from the MgnlContext immediately after execution and added to the response. This example command, called fetchServerDateAndTime, is configured with two such contextParameters, formattedDate and formattedTime, as follows:

```xml
<sv:node sv:name="fetchServerDateAndTime">
  <sv:property sv:name="catalogName" sv:type="String">
    <sv:value>example</sv:value>
  </sv:property>
  <sv:property sv:name="commandName" sv:type="String">
    <sv:value>fetchServerDateAndTime</sv:value>
  </sv:property>
  <sv:node sv:name="contextParameters">
    <sv:property sv:name="jcr:primaryType" sv:type="Name">
      <sv:value>mgnl:contentNode</sv:value>
    </sv:property>
    <sv:property sv:name="date" sv:type="String">
      <sv:value>formattedDate</sv:value>
    </sv:property>
    <sv:property sv:name="time" sv:type="String">
      <sv:value>formattedTime</sv:value>
    </sv:property>
  </sv:node>
```

This appears in the Config tree as:  
<img src="https://github.com/malleusconsulting/magnolia-command-api-example/blob/gh_pages/contextParameters.png?raw=true" width="448" height="217" title="Configuration of contextParameters" />

The command's execute method sets these context parameters and then returns a Boolean as normal:
```Java
MgnlContext.setAttribute(ATTRIBUTE_KEY_DATE, dateFormatter.format(timeNow));
MgnlContext.setAttribute(ATTRIBUTE_KEY_TIME, timeFormatter.format(timeNow));
```

After execution, the [endpoint](http://git.magnolia-cms.com/gitweb/?p=modules/rest.git;a=blob;f=magnolia-rest-services/src/main/java/info/magnolia/rest/service/command/v2/CommandEndpoint.java;h=772ba5a01f37dbc4caf06e59f3c8b894b0286c54;hb=25747bd8f261651458811a7b5751e422470ca83e "CommandEndpoint.java v1.1.1") uses the list of contextParameters to interrogate the MgnlContext and add each parameter to the response.

```Java
commandsManager.executeCommand(command, commandMap);
 resultMap.put("success", true);
 if (contextParameters != null && !contextParameters.isEmpty()) {
   for (String key : contextParameters) {
     resultMap.put(key, MgnlContext.getAttribute(key));
   }
 }
```

And thus the parameters appear in the service response:  
<img src="https://raw.githubusercontent.com/malleusconsulting/magnolia-command-api-example/gh_pages/response.png" width="602" height="329" title="JSON response" />
