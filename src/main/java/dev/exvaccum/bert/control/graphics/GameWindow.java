package dev.exvaccum.bert.control.graphics;

import com.sun.jna.Native;
import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.Utilities;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GLCapabilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40.*;

public class Render{

    private GraphicsEnvironment graphicsEnvironment;
    private boolean isFullScreen;
    public DisplayMode dm, dm_old;
    public Dimension screenDimensions = new Dimension(640,480);
    public float unitsWide = 100;
    private Point point = new Point(0,0);
    float zoomoffset = 0;

    GLFWWindow canvas;

    private GLU glu = new GLU();

    public Render(JFrame frame, int w, int h) {
        capabilities.setAlphaBits(8);
        canvas = new GLJPanel(capabilities);

        canvas.addGLEventListener(this);
        screenDimensions =new Dimension(w,h);
        canvas.setPreferredSize(screenDimensions);
        unitsWide = w;

        final FPSAnimator animator = new FPSAnimator(canvas,144,true);
        frame.getContentPane().add(canvas);
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(animator.isStarted())animator.stop();
                System.exit(0);
            }
        });
        frame.setSize(frame.getContentPane().getPreferredSize());

        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();

        dm_old = devices[0].getDisplayMode();
        dm = dm_old;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int winX = Math.max(0, (screenSize.width - frame.getWidth())/2);
        int winY = Math.max(0, (screenSize.height - frame.getHeight())/2);

        frame.setLocation(winX,winY);
        frame.setVisible(true);

        canvas.addKeyListener(Bert.mBert.input);
        canvas.addMouseListener(Bert.mBert.input);
        canvas.addMouseMotionListener(Bert.mBert.input);
        animator.start();
    }

    public Render(JDialog frame, int w, int h) {
        final GLProfile profile = GLProfile.get(GLProfile.GL4);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setAlphaBits(8);
        canvas = new GLJPanel(capabilities);

        canvas.addGLEventListener(this);
        screenDimensions =new Dimension(w,h);
        canvas.setPreferredSize(screenDimensions);
        unitsWide = (float)screenDimensions.getWidth();

        final FPSAnimator animator = new FPSAnimator(canvas,144,false);
        frame.getContentPane().add(canvas);
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(animator.isStarted())animator.stop();
                System.exit(0);
            }
        });
        frame.setSize(frame.getContentPane().getPreferredSize());

        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();

        dm_old = devices[0].getDisplayMode();
        dm = dm_old;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int winX = Math.max(0, (screenSize.width - frame.getWidth())/2);
        int winY = Math.max(0, (screenSize.height - frame.getHeight())/2);

        frame.setLocation(winX,winY);
        frame.setVisible(true);

        frame.addKeyListener(Bert.mBert.input);
        canvas.addMouseListener(Bert.mBert.input);
        canvas.addMouseMotionListener(Bert.mBert.input);
        animator.start();
    }

    float[] posNs = new float[]{
            0+500+0.0001f,0+500+0.0001f,0f,
            0,0+500+0.0001f,0f,
            0,0,0f,
            0+500+0.0001f,0,0f
    };

    int[] indices = new int[]{
            0,1,2,
            2,3,0
    };

    int[] vao = new int[1];
    FloatBuffer projFB = Buffers.newDirectFloatBuffer(16);
    IntBuffer vertexIB = IntBuffer.allocate(1);
    FloatBuffer vertexFB = FloatBuffer.wrap(posNs);
    IntBuffer ibo = IntBuffer.allocate(1);
    IntBuffer indexIB = IntBuffer.wrap(indices);

    int shader;

    @Override
    public void init(GLAutoDrawable drawable) {
        final Painter pnt = new Painter(drawable, drawable.getGL().getGL4());

        pnt.gl.glClearColor(0f, 0f, 0f, 0f);
        pnt.gl.glClearDepth(1.0f);

        pnt.gl.glEnable(GL_DEPTH_TEST);
        pnt.gl.glDepthFunc(GL_LEQUAL);

        pnt.gl.glEnable(GL_TEXTURE_2D);
        pnt.gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        pnt.gl.glBlendEquationSeparate(GL_FUNC_ADD,GL_MAX);

        pnt.gl.glGenVertexArrays(1,vao,0);
        pnt.gl.glGenBuffers(1,vertexIB);
        pnt.gl.glGenBuffers(1,ibo);

        shader = makeShader(Utilities.parseShader("vertex/Basic.glsl"),Utilities.parseShader("fragment/Basic.glsl"), pnt.gl);
        pnt.gl.glUniformMatrix4fv(pnt.gl.glGetUniformLocation(shader,"m_MVP"), 1, false, projFB);
        pnt.gl.glUniform4f(pnt.gl.glGetUniformLocation(shader,"u_color"), 1.0f,0.0f,1.0f,1.0f);

        pnt.gl.glBindBuffer(GL_ARRAY_BUFFER, vertexIB.get(0));
        pnt.gl.glBufferData(GL_ARRAY_BUFFER, 12 * Native.getNativeSize(float.class),vertexFB,GL_STATIC_DRAW);
        pnt.gl.glBindBuffer(GL_ARRAY_BUFFER,0);

        pnt.gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo.get(0));
        pnt.gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, 6 * Native.getNativeSize(int.class),indexIB,GL_STATIC_DRAW);
        pnt.gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

        pnt.gl.glBindVertexArray(vao[0]);
        pnt.gl.glEnableVertexAttribArray(0);
        pnt.gl.glEnableVertexAttribArray(1);
        pnt.gl.glBindBuffer(GL_ARRAY_BUFFER, vertexIB.get(0));
        pnt.gl.glVertexAttribPointer(0,3,GL_FLOAT,false,Native.getNativeSize(float.class)*3, 0L);
        pnt.gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo.get(0));
        pnt.gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
        pnt.gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        pnt.gl.glBindVertexArray(0);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        final Painter pnt = new Painter(drawable, drawable.getGL().getGL4());
        pnt.gl.glDeleteProgram(shader);
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final Painter pnt = new Painter(drawable, drawable.getGL().getGL4());
        pnt.gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        pnt.gl.glEnable(GL_BLEND);

        pnt.gl.glUseProgram(shader);
        pnt.gl.glUniformMatrix4fv(pnt.gl.glGetUniformLocation(shader,"m_MVP"), 1, false, projFB);
        pnt.gl.glUniform4f(pnt.gl.glGetUniformLocation(shader,"u_color"), 0.0f,0.0f,1.0f,0.5f);

        pnt.gl.glBindVertexArray(vao[0]);

//        pnt.gl.glBindBuffer(GL_ARRAY_BUFFER, vertexIB.get(0));
//
//        pnt.gl.glEnableVertexAttribArray(0);
//        pnt.gl.glVertexAttribPointer(0,3,GL_FLOAT,false,Native.getNativeSize(float.class)*3, 0L);
//
//        pnt.gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo.get(0));

        pnt.gl.glDrawElements(GL_TRIANGLES,6, GL_UNSIGNED_INT,0);

        drawable.swapBuffers();
        pnt.gl.glUseProgram(0);
//        pnt.gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
//        pnt.gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        pnt.gl.glDisable(GL_BLEND);
        pnt.glLogCall();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final Painter pnt = new Painter(drawable, drawable.getGL().getGL4());
        new Matrix4f().ortho(0.0f,(float)drawable.getSurfaceWidth(), (float)drawable.getSurfaceHeight(), 0.0f,-20.0f,20.0f).get(projFB);
    }

    public void paint(Painter pnt){}

    public void setOpaque(boolean opaque){
        canvas.setOpaque(opaque);
    }

    int compileShader(int type, String source, GL4 gl){
        int id =  gl.glCreateShader(type);
        String[] src = new String[]{source};
        gl.glShaderSource(id,1,src,null);
        gl.glCompileShader(id);

        IntBuffer resultBuffer = IntBuffer.allocate(1);
        gl.glGetShaderiv(id, GL_COMPILE_STATUS,resultBuffer);
        if(resultBuffer.get(0)==0){
            IntBuffer lengthBuffer = IntBuffer.allocate(1);
            gl.glGetShaderiv(id, GL_INFO_LOG_LENGTH, lengthBuffer);
            ByteBuffer message = ByteBuffer.allocate(lengthBuffer.get(0));
            gl.glGetShaderInfoLog(id,lengthBuffer.get(0),lengthBuffer,message);
            System.out.println("Failed to compile "+ (type == GL_VERTEX_SHADER?"vertex":"fragment") +" shader!");
            System.out.println(new String(message.array()));
            gl.glDeleteShader(id);
            return 0;
        }
        return id;
    }

    int makeShader(String v, String f, GL4 gl){
        int program = gl.glCreateProgram();
        int vs = compileShader(GL_VERTEX_SHADER, v, gl);
        int fs = compileShader(GL_FRAGMENT_SHADER, f, gl);

        gl.glAttachShader(program, vs);
        gl.glAttachShader(program, fs);
        gl.glLinkProgram(program);
        gl.glValidateProgram(program);

        gl.glDeleteShader(vs);
        gl.glDeleteShader(fs);
        return program;
    }
}
