/**
*	Synology Chat Notifier 0.1
*
*	Author: 
*		Pedro Tobo 
*
*	Documentation: To Do
*
*  Changelog:
*
*	0.1 (May 17 2020) 
*		- Initial release
*
*/

metadata {
	definition (
        name: "Synology Chat Notifier", 
        namespace: "synologychatnotifier", 
        author: "Pedro Tobo",
	    importUrl: "https://github.com/ptobo/hubitat/raw/master/Drivers/SynologyChat/SynologyChatNotifier.groovy") {
		capability "Notification"
    }
    
    preferences {  
		 input "webhookURL", "text", title: "Webhook URL", description: "URL to Synology Chat Hook", defaultValue: "", required: true, displayDuringSetup: true
    }
}


def installed() {
    unsubscribe()
    unschedule()
    initialize()
}

def updated() {

}

private def initialize() {
    
}

def deviceNotification(text){
	try {
        def cmdParams = [
		uri: "${settings.webhookURL}&payload=%7B%22text%22%3A%20%22${text}%22%7D",
		headers: ["Content-Type": "application/x-www-form-urlencoded"],
        query: [text:"${text}"],
        body: "{ text: ${text}}"]
        
        httpPost(cmdParams) { resp ->
			if(resp.status == 200) {
                log.debug "updated ${resp.data}"
				log.debug "Successful notification to Synology Chat"
            }
           	else { log.debug "Failed notification to Synology Chat" }
       }
	} catch (e) {
		log.debug "Something went wrong: $e"
	}
}