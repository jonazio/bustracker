// Create a variable to store the connection
var connection = null;

// Configure a Basic Challenge Handler
var basicHandler = new BasicChallengeHandler();

basicHandler.loginHandler = function(callback) {
    // Create static credentials for test
    var credentials = new PasswordAuthentication("jonas", "jonas");
    callback(credentials);
}

ChallengeHandlers.setDefault(basicHandler);

/*
 Create a function for creating a session and setting up
 Topics, Queues, Consumers, Providers, and Listeners.
 The following function is called when the connection has been created
 but before starting the flow of data.
 */
function setUp() {
    // Typical session options shown.
    alert("skapar conusmer");
    var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    // Add your code here to set up Topics, Queues, Consumers, Providers, and Listeners.
    var topic = session.createTopic("/topic/StompBusTopic");
    var consumer = session.createConsumer(topic);
    consumer.setMessageListener(onMessage);
}

function onMessage(msg) {
    var text = msg.getText();
    alert(msg);
}

// The following function is called when the connection is ready to use and data is flowing.
function connectionStarted() {
    // Add code here when you need to do something after the connection starts.
}

// This function is called from the index.html page when the page loads
function beginConnection(url) {
    // If the connection already exists, call the function for handling running connections.
    if (connection) {
        connectionStarted();
    } else {
        /*
         Create a new object for the JMS connection to a JMS provider via WebSocket
         using the built-in constructor StompConnectionFactory.
         */
        var factory = new StompConnectionFactory(url);
        alert("inte connectionstarted2");
        /*
         Create the actual JMS Connection via WebSocket using the createConnection() method.
         When the connection is created, the callback function with the try...catch is invoked.
         */
        var future = factory.createConnection(function() {
            try {
                alert("inte connectionstarted3");
                /*
                 Once the callback is invoked, client applications should use
                 ConnectionFuture.getValue() to fetch the actual connection.
                 getValue() returns the value from an asynchronous operation.
                 */
                connection = future.getValue();
                /*
                 With the connection established, but before starting the flow of data,
                 create the session.
                 */
                setUp();
                /*
                 Call the function to use when the connection
                 is ready to use and data is flowing.
                 */
                connection.start(connectionStarted);
            } catch(e) {
                alert("inte connectionstarted32");
                alert(e.message);
            }
        });

        alert("inte connectionstarted4");
    }
}
