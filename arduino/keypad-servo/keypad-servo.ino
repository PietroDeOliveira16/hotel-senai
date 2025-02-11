#include <Key.h>
#include <Keypad.h>

#include <Servo.h>
#include <string.h>

const byte quantidadeLinhas = 4;
const byte quantidadeColunas = 3;

char matriz_teclas[quantidadeLinhas][quantidadeColunas] = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}, {'*', '0', '#'}};

byte pinosLinhas[quantidadeLinhas] = {4, 5, 6, 7};
byte pinosColunas[quantidadeColunas] = {8, 9, 10};

Keypad teclado = Keypad(makeKeymap(matriz_teclas), pinosLinhas, pinosColunas, quantidadeLinhas, quantidadeColunas);

String senhaAtual = "";

Servo servo;
unsigned long tempoMaximo = 5000;

int ledErro = 12;
int ledAcerto = 11;

int buzzer = 13;

bool checkTimeout = false;
bool permitirAcesso = false;

String inputBuffer = " ";

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  servo.attach(2);
  servo.write(0);

  pinMode(ledErro, OUTPUT);
  pinMode(ledAcerto, OUTPUT);
  pinMode(buzzer, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  char teclaPressionada = teclado.getKey();
  unsigned long tempoPrograma = millis();

  if(senhaAtual != ""){
    while(!checkTimeout){
      teclaPressionada = teclado.getKey();
      if(teclaPressionada){
        checkTimeout = false;
        break;
      }

      if(millis() - tempoPrograma >= tempoMaximo){
        digitalWrite(ledErro, HIGH);
        tone(buzzer, 220, 800);
        senhaAtual = "";
        checkTimeout = true;
      }
    }
  }

  EnviarInput(teclaPressionada);

  if(teclaPressionada && teclaPressionada != '#'){
    senhaAtual += teclaPressionada;
    tone(buzzer, 335, 300);
  }

  if(teclaPressionada == '*'){
    senhaAtual = "";
    tone(buzzer, 335, 300);
  }

  delay(500);
  checkTimeout = false;
  digitalWrite(ledErro, LOW);
  digitalWrite(ledAcerto, LOW);
}

void EnviarInput(char teclaPressionada){
  if(teclaPressionada == '#'){
    tone(buzzer, 335, 300);
    Serial.println(senhaAtual);
    delay(2000);
    inputBuffer = Serial.readString();
    if(inputBuffer.equalsIgnoreCase("true")){
      servo.write(180);
      senhaAtual = "";
      digitalWrite(ledAcerto, HIGH);
      tone(buzzer, 375, 1000);
      delay(5000);
      servo.write(0);
    }else{
      servo.write(0);
      digitalWrite(ledErro, HIGH);
      tone(buzzer, 220, 800);
      senhaAtual = "";
    }
    Serial.flush();
    inputBuffer = " ";
  }
}
