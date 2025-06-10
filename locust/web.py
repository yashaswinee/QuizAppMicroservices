# test_server.py
# this file is used to run a simple Flask server for testing purposes

from flask import Flask
app = Flask(__name__)

@app.route('/hello')
def hello():
    return 'Hello!'

@app.route('/world')
def world():
    return 'World!'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8000)