import user
import requests

def listar():
    resposta.request.get("http://localhost:8000/cuser/user")
    resultado - resposta.json()

    print("=====================================lista===================================")
    for user in resultado:
        print(user)


if __name__ == '__main__':
    listar()