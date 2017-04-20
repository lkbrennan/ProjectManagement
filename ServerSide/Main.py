from http.server import BaseHTTPRequestHandler, HTTPServer
import simplejson
import sqlite3
import urllib
#skeleton code found online
class Server(BaseHTTPRequestHandler):

    def do_GET(self):
        file = open("WriteFile.txt", "r+")

        # Send response status code
        self.send_response(200)
        # Send headers
        self.send_header('Content-type', 'application/json')
        self.end_headers()
        print("responded")
        sender = urllib.parse.urlparse(self.path)

        input = urllib.parse.parse_qsl(sender[4])
        group1= input[0]
        group2= input[1]
        item1=group1[1]
        item2=group2[1]
        data=GrabUser(item1,item2)
        print(data)
        self.wfile.write(bytes(data, 'UTF-8'))
        return data

    def do_POST(self):
        file = open("WriteFile.txt", "r+")
        dave = file.read()

        try:

            self.send_response(200,"successful post")
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            content_length = int(self.headers['Content-Length'])
            output = simplejson.loads(self.rfile.read(content_length))
            file.write("\n"+output['name'] + " " + output['password'])
            PostUser(output['name'],output['password'])
            file.close()
            return
        except:
            pass

def PostUser(name,password):

    try:
        database = sqlite3.connect('data/userInf.db')
        cursor = database.cursor()
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS user(name TEXT,password TEXT)
        ''')

        database.commit()
        cursor.execute('''INSERT INTO user(name,password) VALUES(?,?)''', (name,password))
        database.commit()
        cursor.execute('''SELECT * FROM user''')
        user1 = cursor.fetchone()
        print(user1[0])
    except sqlite3.OperationalError as msg:
        print(msg)
        raise msg
    finally:
        database.close()

def GrabUser(name, password):
    database = sqlite3.connect('data/userinf.db')
    cursor = database.cursor()
    cursor.execute('''SELECT password FROM user WHERE name = ?''', (name,))
    user1=""
    user1=cursor.fetchone()
    if user1[0]== password:
        return '0'
    else:
        return '1'
    
def groupt(gId, gName, admin, adminIp):

    try:
        database = sqlite3.connect('data/userInf.db')
        cursor = database.cursor()
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS groups(gId INTEGER,gName TEXT,admin TEXT,adminIp INTEGER,PRIMARY KEY(gId))
        ''')
        database.commit()
        cursor.execute('''INSERT INTO groups(gId,gName,admin,adminIp) VALUES(?,?,?,?)''', (gId,gName,admin,adminIp))
        database.commit()
        cursor.execute('''SELECT * FROM groups''')
        group1 = cursor.fetchone()
        print(group[0])
    except sqlite3.OperationalError as msg:
        print(msg)
        raise msg
    finally:
        database.close()

def GrabGroupt(gId, gName, admin, adminIp):
    database = sqlite3.connect('data/userinf.db')
    cursor = database.cursor()
    cursor.execute('''SELECT gId FROM groups WHERE gName = ?''', (gName,))
    group1 = ""
    group1 = cursor.fetchone()
    


def run():
    print('starting server...')

    server_address = ('192.168.192.46', 8082)
    httpd = HTTPServer(server_address, Server)
    print('running server...')
    httpd.serve_forever()


run()
