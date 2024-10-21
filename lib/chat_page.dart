import 'package:google_generative_ai/google_generative_ai.dart';
import 'package:flutter/material.dart';
import 'package:flutter_chat_types/flutter_chat_types.dart' as types;
import 'package:flutter_chat_ui/flutter_chat_ui.dart';
import 'dart:math';

import 'package:malayah/const.dart';

class ChatPage extends StatefulWidget {
  const ChatPage({super.key});

  @override
  State<ChatPage> createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {
  final _user = const types.User(id: "1", firstName: "Harry", lastName: "Pota");
  final _aiUser = const types.User(id: "2", firstName: "Malayah", lastName: "Bot");
  final List<types.Message> _messages = [];

  final model = GenerativeModel(
      model: 'gemini-1.5-flash-latest',
      apiKey: API_KEY,
      safetySettings: [
        SafetySetting(HarmCategory.dangerousContent, HarmBlockThreshold.none)
      ],
      generationConfig: GenerationConfig(maxOutputTokens: 200));

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 230, 158, 162),
        title: const Text(
          'Malayah',
          style: TextStyle(
            color: Colors.white,
          ),
        ),
      ),
      body: Chat(
        messages: _messages,
        onSendPressed: _handleSendPressed,
        user: _user,
        theme: const DefaultChatTheme(
          inputBackgroundColor: Color.fromARGB(255, 230, 158, 162),
        ),
      ),
    );
  }

  void _handleSendPressed(types.PartialText msg) {
    final message = types.TextMessage(
      author: _user, 
      id: generateRandomString(), 
      text: msg.text,
    );

    _getChatResponse(message);
  }

  Future<void> _getChatResponse(types.TextMessage msg) async {
    setState(() {
      _messages.insert(0, msg);
    });

    final content = Content.text(msg.text);

    final response = await model.generateContent([content]);

    if (response.text != null) {
      setState(() {
        _messages.insert(0, types.TextMessage(
                              author: _aiUser, 
                              id: generateRandomString(), 
                              text: response.text!,
                            )
        );
      });
    }
  }

  String generateRandomString() {
    var r = Random();
    return String.fromCharCodes(List.generate(10, (index) => r.nextInt(33) + 89));
  }
}