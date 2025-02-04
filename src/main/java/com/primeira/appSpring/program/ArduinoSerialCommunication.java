package com.primeira.appSpring.program;
import com.primeira.appSpring.controller.C_ValidaSenha;
import com.fazecast.jSerialComm.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ArduinoSerialCommunication {

    private SerialPort serialPort;
    private C_ValidaSenha c_validaSenha;

    public static void main(String[] args) {
        ArduinoSerialCommunication arduino = new ArduinoSerialCommunication();
        arduino.initialize();
    }

    public void initialize() {
        SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length == 0) {
            System.out.println("Nenhuma porta serial encontrada.");
            return;
        }

        serialPort = ports[0]; // Use a primeira porta serial encontrada, você pode ajustar isso conforme necessário

        if (!serialPort.openPort()) {
            System.out.println("Falha ao abrir a porta serial.");
            return;
        }

        serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 0);


        BufferedReader input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        OutputStream output = serialPort.getOutputStream();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (input.ready()) {
                    String inputLine = input.readLine();
                    System.out.println("Dados recebidos: " + inputLine);
                    String outputString = c_validaSenha.ChecarSenha(inputLine);
                    if(!outputString.isEmpty()) {
                        output.write(outputString.getBytes());
                    }
                    output.flush();
                    try{Thread.sleep(100);} catch (Exception e){}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}