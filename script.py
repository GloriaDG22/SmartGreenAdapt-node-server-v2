import requests

# PARAMETERS RESTRICTIONS
# temperature error < -7 | 8 > warning > 35 | error > 46
# luminosity error < -1 | 0 > warning > 1000 | error > 1500
# airquality error < -1 | 0 > warning > 50 | error > 200
# humidity error < 0 | 50 > warning > 90 | error > 100

# DEFINITION
# GET / pedir datos de la información para procesar los datos con los valores anteriores
# POST / subir los datos anómalos como notificaciones de error o alertas

# Mandar notificacion a Android

# COMMAND EXECUTE
# cd D:Documents/Semestre9/CatedraTelefonica/SmartGreenAdapt/nodejs-server-server
# python .\script.py

# ----------------------- TEMPERATURE -----------------------
information = requests.get('http://192.168.1.104:8080' + '/temperature').json()['message']

data = {}
problem = False

for item in information:
    if -7 <= item['amount'] < 8 :
        problem = True
        data['is_warning'] = 0 #True
        data['status'] = 'low'

    elif 35 < item['amount'] <= 46 :
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
    if 1000 < item['amount'] <= 1500 :
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
    if 50 < item['amount'] <= 200 :
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
    if 0 <= item['amount'] < 50 :
        problem = True
        data['is_warning'] = 0 #True
        data['status'] = 'low'

    elif 90 < item['amount'] <= 100 :
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