import requests

# PARAMETERS RESTRICTIONS DEFECT
# temperature error < -7 | 8 > warning > 35 | error > 46
# luminosity error < -1 | 0 > warning > 1000 | error > 1500
# airquality error < -1 | 0 > warning > 50 | error > 200
# humidity error < 0 | 50 > warning > 90 | error > 100

# CONTENT FILE
# Name sensor
# [minimum value sensor]
# [maximum value sensor]

# DEFINITION
# GET / pedir datos de la información para procesar los datos con los valores anteriores
# POST / subir los datos anómalos como notificaciones de error o alertas

# Mandar notificacion a Android

# COMMAND EXECUTE
# cd D:Documents/Semestre9/CatedraTelefonica/SmartGreenAdapt/nodejs-server-server
# python .\script.py

with open('valoresSensores.py') as f:
    f.readline() # temperature 
    minTem = f.readline()
    maxTem = f.readline()
    
    f.readline() # luminosity
    maxLum = f.readline()
    
    f.readline() # airquality
    maxAir = f.readline()

    f.readline() # humidity
    minHum = f.readline()
    maxHum = f.readline()
    
# ----------------------- TEMPERATURE -----------------------
information = requests.get('http://192.168.1.104:8080' + '/temperature').json()['message']

data = {}
problem = False

for item in information:
    if -7 <= item['amount'] < minTem :
        problem = True
        data['is_warning'] = 0 #True
        data['status'] = 'low'

    elif maxTem < item['amount'] <= 46 :
        problem = True
        data['is_warning'] = True
        data['status'] = 'high'

    elif item['amount'] < -7 or item['amount'] > 46:
        problem = True
        data['is_warning'] = 1 #False
        data['status'] = 'error'

    if problem: 
        data['problem'] = 'Temperature'
        data['date'] = item['date']
        data['idInformation'] = item['id']

        print("problem temp")
        # Notification
        requests.post(url="http://192.168.1.104:8080/notification", json=data)
    else:
        print("no problem temp")

# ----------------------- LUMINOSITY -----------------------
information = requests.get('http://192.168.1.104:8080' + '/luminosity').json()['message']

data = {}
problem = False

for item in information:
    print(item)
    if maxLum < item['amount'] <= 1500 :
        problem = True
        data['is_warning'] = 0 
        data['status'] = 'high'

    elif item['amount'] < 0 or item['amount'] > 1500:
        problem = True
        data['is_warning'] = 1
        data['status'] = 'error'

    if problem: 
        data['problem'] = 'Luminosity'

        print("problem lum")
        # Notification
        requests.post(url="http://192.168.1.104:8080/notification", json=data)
    else:
        print("no problem lum")

# ----------------------- AIQUALITY -----------------------
information = requests.get('http://192.168.1.104:8080' + '/airquality').json()['message']

data = {}
problem = False

for item in information:
    print(item)
    if maxAir < item['amount'] <= 200 :
        problem = True
        data['is_warning'] = 0 
        data['status'] = 'high'

    elif item['amount'] < 0 or item['amount'] > 200:
        problem = True
        data['is_warning'] = 1
        data['status'] = 'error'

    if problem: 
        data['problem'] = 'Airquality'

        print("problem air")
        # Notification
        requests.post(url="http://192.168.1.104:8080/notification", json=data)
    else:
        print("no problem air")


# ----------------------- HUMIDITY -----------------------
information = requests.get('http://192.168.1.104:8080' + '/humidity').json()['message']

data = {}
problem = False

for item in information:
    if 0 <= item['amount'] < minHum :
        problem = True
        data['is_warning'] = 0 #True
        data['status'] = 'low'

    elif maxHum < item['amount'] <= 100 :
        problem = True
        data['is_warning'] = True
        data['status'] = 'high'

    elif item['amount'] < 0 or item['amount'] > 100:
        problem = True
        data['is_warning'] = 1 #False
        data['status'] = 'error'

    if problem: 
        data['problem'] = 'Humidity'

        print("problem hum")
        # Notification
        requests.post(url="http://192.168.1.104:8080/notification", json=data)
    else:
        print("no problem hum")