# (EN) Visual attention asymmetry test image generator

An application for generating images to use in psychiatric tests for estimating an individual's visual attention asymmetry.

The image (10x12 cm) contains randomly distributed upper-case English letters, 314 of which are "distractors" (symbols in range B-Z), and 60 are the "target" letters 'A'.  
The 60 "A"s are symmetrical with respect to the vertical center of the image.  
The testee is expected to find and mark as many "A"s as possible in 1 minute of the test.
  
Two versions of images are generated:
* A black and white version without a grid - to be handed to the testee.  
![Black-and-white version](https://raw.githubusercontent.com/i-sergienko/visual-attention-test-generator/main/example/noColor.jpg)  

* A colored version (the A's are marked in red) with a grid, for the reviewer's convenience.
![Colored version](https://raw.githubusercontent.com/i-sergienko/visual-attention-test-generator/main/example/colored.jpg)  

  
Authors:
* Implemented by Ivan Sergienko
* Specifications by Alexey Sergienko

# (RU) Генератор таблиц для методики оценки асимметрии зрительного произвольного внимания (АЗВ)

Стимульный материал:  карточки (пример см. ниже), каждая из которых представляет собой очерченный на половине листа А4 прямоугольник 10×12 см, в котором, среди 157 дистракторов (букв латинского алфавита) с каждой стороны, симметрично относительно средней линии прямоугольника, разбросаны по 30 стимулов (буква А латинского алфавита).  

Инструкция: «Тебе (Вам) нужно на листочке найти и обвести в кружок все буквы «А», как можно быстрее».  

На задание дается 1 минута.
Считается, что испытуемые с функциональной слабостью левого полушария игнорируют правую часть зрительного поля. И наоборот, испытуемые с функциональной слабостью правого полушария игнорируют левую часть зрительного поля и, следовательно, зрительное внимание в игнорируемой части снижается.  

Предлагается программный «Генератор таблиц» для методики АЗВ который позволяет случайным образом располагать стимулы среди дистракторов при этом удовлетворяя правилу количественного и качественного распределения стимулов с каждой стороны листа.
  
Программа генерирует 2 версии карточек:
* Черно-белая версия без сетки, для тестируемого.  
![Black-and-white version](https://raw.githubusercontent.com/i-sergienko/visual-attention-test-generator/main/example/noColor.jpg)  

* Версия с символами "А" выделенными красным, и с сеткой - для проверяющего.
![Colored version](https://raw.githubusercontent.com/i-sergienko/visual-attention-test-generator/main/example/colored.jpg)  
  
Авторы:
* Имплементация: Иван Сергиенко
* Техническое задание: Алексей Сергиенко