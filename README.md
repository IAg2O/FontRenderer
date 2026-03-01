# <h3 align="center">FontRenderer</h3>
### <h3 align="center">[中文](#中文) / [English](#english)</h3>

## 中文
**FontRenderer** 是一个基于 **OpenGL** 与 **Java AWT** 的轻量化高性能字体渲染器，后续计划迁移至 **Vulkan**。


将持续开源并维护。如果它对你有帮助，可以点个 __Star__ 支持一下。

用法：

```java

private final static Backend backend = new OpenGLBackedn();
private final static GLFontRenderer renderer = new GLFontRenderer(backend, yourFont);

renderer.beginTexture();

renderer.pushText("Text", x, y, Color.BLACK);
renderer.flush();

renderer.end();

```

### 本项目采用 [GNU GPL v3](/LICENSE) 开源协议。

## English

FontRenderer is a lightweight, high-performance font rendering engine built on **OpenGL** and **Java AWT**. A migration to **Vulkan** is planned for future updates to further enhance efficiency.

This project is fully open-source and actively maintained. If you find it useful, a Star would be greatly appreciated!

Usage:

```java

private final static Backend backend = new OpenGLBackedn();
private final static GLFontRenderer renderer = new GLFontRenderer(backend, yourFont);

renderer.beginTexture();

renderer.pushText("Text", x, y, Color.BLACK);
renderer.flush();

renderer.end();

```

### This project is licensed under [GNU GPL v3](/LICENSE)