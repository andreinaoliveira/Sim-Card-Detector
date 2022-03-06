<div align = "center">
  <img width = "120" src = "https://user-images.githubusercontent.com/51168329/156935527-fd419369-917d-4391-80ca-d16706b0e7a3.png">
  <h1>Projeto SIM Card</h1>
  <h4>Time 06</h4>
</div>

##
<div align = "center">
  <h5> Mecanismo de segurança do SIM Card na camada do AOSP </h5> 
</div>

##

<div align = "center">
    <p align ="center">
    <a href="#-objetivo">📍 Objetivo</a>
    <a href="#-funcionalidades">📍 Funcionalidades</a>
    <a href="#-tecnologias">📍 Tecnologias</a>
    <a href="#%EF%B8%8F-desenvolvimento">📍 Desenvolvimento</a>
    <a href="#-time">📍 Time</a>
    </p>
</div>


## 📝 Objetivo

- Adicionar no AOSP um recurso que salve um identificador do SIM Card para detectar e notificar se houver
troca por outro SIM Card. 
- Implementar um controle do SIM Card em uso na camada do AOSP. 

## ✨ Funcionalidades

- 💾 Visualização e consulda dos ICCID's e detalhos do chip.

- 📲 Notificação ao inserir um SIM Card novo, ou se houver troca.

- 📋 Histórico dos SIM Cards

- ✅ Controle de segurança dos SIM Cards.

## 🛠 Tecnologias 

As seguintes tecnologias foram usadas na construção do projeto:

| Kotlin | Java | GitLab | Android Studio | VS Code |
| ------------ | ------------- | ------------- | ------------- | ------------- |
| <a href="https://kotlinlang.org/"> <img src="https://seeklogo.com/images/K/kotlin-logo-30C1970B05-seeklogo.com.png" width="90"> </a> | <a href="https://www.java.com/pt-BR/"> <img src="https://cdn.worldvectorlogo.com/logos/java-14.svg" width="90"> </a> | <a href="https://about.gitlab.com/"> <img src="https://www.justsoftware.com.br/assets/images/GitLab_Logo.svg.png" width="90"> </a> |<a href="https://developer.android.com/"> <img src="https://1.bp.blogspot.com/-LgTa-xDiknI/X4EflN56boI/AAAAAAAAPuk/24YyKnqiGkwRS9-_9suPKkfsAwO4wHYEgCLcBGAsYHQ/s0/image9.png" width="90"> </a> |<a href="https://code.visualstudio.com/"> <img src="https://cdn.freebiesupply.com/logos/large/2x/visual-studio-code-logo-png-transparent.png" width="90"> </a> |

## 🖥️ Desenvolvimento
Visando incluir um mecanismo de segurança para o SIM Card de forma
nativa, o projeto em questão utiliza o AOSP como base para a criação de uma ROM
personalizada para o Android 9.0 (Pie). A ROM em questão conta com modificações
na camada de telefonia para identificação de inserção e remoção de SIM Cards,
além de armazenar os dados SIM Card. Através do mecanismo de segurança
implementado no AOSP, o APP criado no projeto “SIM Card”, monitora a entrada
e saída de SIM 's, apresenta ao usuário o histórico dos cartões, além de identificar
dados como ICCID, Número, Operadora, entre outros.<br><br>
<b>
Para acesso a documentação do projeto na qual explica todas as etapas do desenvolvimento acessar <a href='https://github.com/andreinaoliveira/Sim-Card-Detector/blob/master/%23%20Sobre%20o%20App/CONTROLE%20SIM%20CARD%20%C3%80%20N%C3%8DVEL%20AOSP.pdf'>Controle Sim Card à nível AOSP</a>
</b>
<br><br>
<img src = "https://user-images.githubusercontent.com/51168329/156935831-9975de71-4d94-4036-a627-5b3986d974d3.png">

## 🤝 Time

<table>
  <tr>
    <td align="center"><br><img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/51168329/156936028-4e2f4b91-c5ee-40a8-9c62-c3d227add69c.png" width="100px;" alt=""/><br /><sub><b>Andreina Oliveira</b></sub></a><br>Scrum Master<br>Developer</td>
    <td align="center"><img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/51168329/156935899-87bd49c9-6927-4ba2-a357-02e687444134.png" width="100px;" alt=""/><br /><sub><b>Andre Rondi</b></sub></a><br>Developer</td>
    <td align="center"><img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/51168329/156935924-8456ffcc-665e-477a-816e-6349cc6269df.png" width="100px;" alt=""/><br /><sub><b>Sthefanye Guimarães</b></sub></a><br>Developer</td>
    <td align="center"><img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/51168329/156935974-5bf52532-3c0a-475f-80b8-fcd24f868062.png" width="100px;" alt=""/><br /><sub><b>Wesllen Vasconcelos</b></sub></a><br>Developer</td>
  </tr>
</table>
<div>
  
  <h5> <a href="#top">Volte ao topo</a> </h5>

</div>
