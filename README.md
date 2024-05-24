# jornada-digital-arquitetura-I

# Princípios do SOLID aplicados
## 1 - Segregação de interfaces em tech.ada.banco.service.ContaCorrentePFService
Esta classe mostra como a segregação de interfaces está presente no projeto, pois para cada tipo de operação existe uma interface definindo o comportamento. Exemplo são as interfaces ConsultaSaldo<ContaCorrente>, Deposito<ContaCorrente>,
SaquePFImpl<ContaCorrente>, TransferenciaPFImpl<ContaCorrente>.
Todas estas interfaces poderiam estar resumidas em uma ou duas interfaces de operações, mas a divisão em mais interfaces faz com que esta clase service, por exemplo, não precise implementar métodos de investimento. 
Pois esta operação está separada em uma interface própria. 

## 2 - Princípio da responsabilidade única em tech.ada.banco.service.ContaCorrentePFService

Nesta classe foram concentradas apenas as operações relacionadas a ContaCorrente Pessoa Física, para os outros tipos de conta, outras classes devem operar.

## 3 - Open/Closed Principle aplicado em State na interface tech.ada.banco.model.SituacaoConta e suas implementações
Junto do pattern State implementado na interface SituacaoConta, o princípio Open/Closed é aplicado, pois novos estados podem ser adicionados sem alterar o código dos estados existentes.

## 4 - Liskov Substitution Principle em tech.ada.banco.model.ContaCorrente e tech.ada.banco.model.ContaPoupanca
Estas duas classes extendem a classe Conta, e são capazes de substituir a classe mãe sem alterar o comportamento do sistema.
Todos os atributos existentes na classe mãe são pertinentes nessas duas classes filhas, portanto o princípio de substituição é bem aplicado.

## 5 - Princípio da inversão de dependência em tech.ada.banco.service.ContaCorrentePFService

Nesta classe, a inversão de dependência é aplicada, pois a classe depende de interfaces, e não de classes concretas.
A classe service é uma classe de mais alto nível comparado às classes repository da qual ela depende. Portanto, o fato de depender das interfces deses repositórios é uma recomendação do princípio sendo aplicada.


# 5 exemplos de Design Patterns aplicados no projeto

## 1 - Chain of responsibility em tech.ada.banco.login.JwtAuthFilter, linha 36 e outras

Extendendo a classe OncePerRequestFilter, esta classe é capaz de sobrescrever o método doFilterInternal(), encadeando o comportamento desejado na cadeia de filtros que tratam a requisição HTTP. 
Neste exemplo o objetivo é capturar e validar o token de autenticação presente no Header da requisição.

## 2 - Builder em tech.ada.banco.usuario.UsuarioDTO, na linha 13
Com a anotação @Builder o pacote Lombok, usado como dependência do projeto, gera a implementação do design pattern Builder na classe. 
Na prática é criada uma classe interna builder, que possui métodos que definem todos os parâmetros da classe mãe, e no final um método build, que retorna uma instância da clase mãe com os atributos informados através dos métodos anteriores.

## 3 - Padrão Factory implementado em tech.ada.banco.login.JWTFactory
Criada a interface JWTFactory, que define o método createJWT(), e a classe JWTFactoryImpl, que implementa a interface e retorna uma instância de JWT.
A classe JWTFactoryImpl é injetada na classe JwtService, e o método createJWT() é chamado para criar uma instância de JWT no método createToken.

Essa implementação permite abstrair a criação de um objeto JWT a partir dos dados do usuário. Traz a possibilidade de gerar outros tipos de token no futuro, caso necessário.

## 4 - Singleton em tech.ada.banco.util.SimpleLogger

A classe SimpleLogger é um Singleton, ou seja, só pode ser instanciada uma vez.
O método getInstance() retorna a instância da classe, e o construtor é privado, impedindo a criação de novas instâncias.
A classe é um exemplo simples para impressão de logs, mas o ponto focal do pattern é o método getInstance() utilizado para obter a instância única da classe. 
Ele é chamado em ContaInvestimentoPFService, na linha 27.

## 5 - State na interface tech.ada.banco.model.SituacaoConta e suas implementações

Para representar três estados possíveis de uma conta, foi criada a interface SituacaoConta.
As transições de situação possíveis são: conta ativa, conta encerrada e conta bloqueada.
Para representar estes estados foram criadas as 3 classes que implementam SituacaoConta:
- ContaAtiva
- ContaEncerrada
- ContaBloqueada

A implementação do padrão State permite que o comportamento de uma classe mude de acordo com o estado em que ela se encontra.
Na implementação realizada no projeto, os estados ContaEncerrada e ContaBloqueada não permitem novo bloqueio, por exemplo.
Já o estado ContaAtiva permite que a conta seja bloqueada.
