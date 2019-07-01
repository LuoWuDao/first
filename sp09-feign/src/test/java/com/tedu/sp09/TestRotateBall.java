package com.tedu.sp09;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestRotateBall {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        // 设置窗体大小
        frame.setSize(700, 700);
        // 设置窗体的相对位置
        frame.setLocationRelativeTo(null);
//        frame.setLocation(1400, 610);
        // 设置关闭窗口的策略
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Jpanel画布初始化
        TestRotateBall test = new TestRotateBall();
        Color back = new Color(238, 238, 238);
        TestRotateBall.MyPanel panel = test.getPanelInstance(350, 300, back);
        panel.setBackground(back);
        // 添加并显示
        frame.add(panel);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        // 主控函数
        panel.rotateRings();
    }

    /**
     * 成员内部类---Panel画布
     */
    class MyPanel extends JPanel {

        // 基准坐标
        private int baseX;
        private int baseY;
        // 背景颜色
        private Color backGround;
        // 环 1、2、3
        private RingGap ring1st;
        private RingGap ring2nd;
        private RingGap ring3rd;
        // 运行时间
        private long time = 0;
        // 球1、2、3、4、5
        private RotateBall ball1;
        private RotateBall ball2;
        private RotateBall ball3;
        private RotateBall ball4;
        private RotateBall ball5;

        /**
         * 构造器：初始化，创建了环对象和球对象
         */
        public MyPanel(int x, int y, Color c) {

            baseX = x;
            baseY = y;
            backGround = c;
            //球属性
            // 球的转动半径
            int ballRotateRadius = 260;
            // 球的颜色
            Color ballsColor = new Color(31, 189, 193);
            // 球的直径大小
            int ballSize = 50;

            //
            ring1st = new RingGap(baseX, baseY, 300, 20, 0, -30,
                    new Color(9, 164, 168));
            ring2nd = new RingGap(baseX, baseY, 260, 20, 90, 170,
                    new Color(69, 14, 168));
            ring3rd = new RingGap(baseX, baseY, 220, 20, 70, -100,
                    new Color(212, 32, 50));

            // 设置运动间隔
            ring1st.setInterval(3);
            ring2nd.setInterval(7);
            ring3rd.setInterval(10);
            // 设置运动方向
            ring1st.setDirection(true);
            ring2nd.setDirection(false);
            ring3rd.setDirection(true);

            ball1 = new RotateBall(baseX, baseY, 90, ballRotateRadius, ballSize, ballsColor);
            ball2 = new RotateBall(baseX, baseY, 110, ballRotateRadius, ballSize, ballsColor);
            ball3 = new RotateBall(baseX, baseY, 130, ballRotateRadius, ballSize, ballsColor);
            ball4 = new RotateBall(baseX, baseY, 150, ballRotateRadius, ballSize, ballsColor);
            ball5 = new RotateBall(baseX, baseY, 210, ballRotateRadius, ballSize, ballsColor);
        }

        /**
         * 画图的主方法，repaint()刷新的也是此方法
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            // 画布的画图集成方法
            paintGraph(g);
            // 写中心字符的方法
            myStringPaint(baseX, baseY, getCurrentTime(), 28, g, new Color(181, 5, 7));
        }

        /**
         * 主控函数
         */
        public void rotateRings() {
            // 时序间隔时间，单位为毫秒
            int inteval = 1;
            while (true) {
                /*
                 * 时序控制，一次循环为一次基本时序
                 */
                // 环运动控制
                controlRing(ring1st, time);
                controlRing(ring2nd, time);
                controlRing(ring3rd, time);
                // 随机环颜色
                /*colorfulRing(ring1st, time);
                colorfulRing(ring2nd, time);
                colorfulRing(ring3rd, time);*/
                brightColorRing(ring1st, time);
                brightColorRing(ring2nd, time);
                brightColorRing(ring3rd, time);
                // 球运动控制
                controlBall(ball1, time);
                controlBall(ball2, time);
                controlBall(ball3, time);
                controlBall(ball4, time);
                controlBall(ball5, time);

                time++;
                MySleep(inteval);
                this.repaint();
            }
        }

        /**
         * 返回当前时间的字符串
         *
         * @return 形如 xx:xx:xx 的字符串
         */
        private String getCurrentTime() {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            return format.format(new Date());
        }

        /**
         * 球移动控制
         */
        private void controlBall(RotateBall ball, long time) {
            // 当球运动时重新计算坐标
            if (time % ball.getInteval(ball.getAngle()) == 0) {
                ball.setAngle(ball.getAngle() - 1);
            }
        }

        /**
         * 环移动控制
         */
        private void controlRing(RingGap ring, long time) {
            int prenum, times = 1;
            if (ring.getDirection()) {
                prenum = 1;
            } else {
                prenum = -1;
            }
            // 运动方式是一个周期移动缺口位置
            if (time % ring.getInterval() == 0) {
                ring.setStartAngle(ring.getStartAngle() + prenum * times);
            }
        }

        /**
         * 环颜色随机方法
         * 经验证存在问题，环的颜色总是趋于饱和度低（三基色的值基本相等）原因：
         * 若没有0-255的参数限制，这样的随机方式可行，但由于参数限制
         * 人工添加了离开边界的方式，所以一定会趋向边界的反方向，也就是两个边界的中心
         * <p>
         * 该方法已被废弃
         * 被替换为 brightColorRing(RingGap ring, long time)
         */
        @Deprecated
        private void colorfulRing(RingGap ring, long time) {
            int inteval = 5;
            if (time % inteval == 0) {
                Color now = ring.getColor();
                int flag = (int) (Math.random() * 100); // 类型转换优先级问题
                //修改R值
                if (now.getRed() == 0) {
                    ring.setColor(new Color(1, now.getGreen(), now.getBlue()));
                    return;
                }
                if (now.getRed() == 255) {
                    ring.setColor(new Color(254, now.getGreen(), now.getBlue()));
                    return;
                }
                if (flag <= 49) {
                    ring.setColor(new Color(now.getRed() + 1, now.getGreen(), now.getBlue()));
                } else {
                    ring.setColor(new Color(now.getRed() - 1, now.getGreen(), now.getBlue()));
                }
                now = ring.getColor();
                // 修改G值
                if (now.getGreen() == 0) {
                    ring.setColor(new Color(now.getRed(), 1, now.getBlue()));
                    return;
                }
                if (now.getGreen() == 255) {
                    ring.setColor(new Color(now.getRed(), 254, now.getBlue()));
                    return;
                }
                if (flag <= 49) {
                    ring.setColor(new Color(now.getRed(), now.getGreen() + 1, now.getBlue()));
                } else {
                    ring.setColor(new Color(now.getRed(), now.getGreen() - 1, now.getBlue()));
                }
                now = ring.getColor();
                // 修改B值
                if (now.getBlue() == 0) {
                    ring.setColor(new Color(now.getRed(), now.getGreen(), 1));
                    return;
                }
                if (now.getBlue() == 255) {
                    ring.setColor(new Color(now.getRed(), now.getGreen(), 254));
                    return;
                }
                if (flag <= 49) {
                    ring.setColor(new Color(now.getRed(), now.getGreen(), now.getBlue() + 1));
                } else {
                    ring.setColor(new Color(now.getRed(), now.getGreen(), now.getBlue() - 1));
                }
            }
        }

        /**
         * 新的颜色随机方法，新增了一种取随机数的方法getFactor(),
         * 使得随机的颜色不再趋于灰色，而是饱和度更高的更明亮的颜色
         *
         * @param ring
         * @param time
         */
        private void brightColorRing(RingGap ring, long time) {
            // 颜色改变的时间间隔
            int inteval = 5;
            // 跳出
            if (time % inteval != 0) {
                return;
            }
            // 获取随机改变颜色种类
            int color = new Random().nextInt(3);
            // 改变符号的随机数 0 - 99
            int flag = new Random().nextInt(100);

            // 获取上一时刻的颜色信息
            Color now = ring.getColor();
            int red = now.getRed();
            int green = now.getGreen();
            int blue = now.getBlue();

            // 随机产生颜色
            if (color == 0) {
                // 如果是红色
                // 离开颜色边界
                if (red == 0) {
                    ring.setColor(new Color(red + 1, green, blue));
                    return;
                }
                if (red == 255) {
                    ring.setColor(new Color(red - 1, green, blue));
                    return;
                }
                // 随机新近似的颜色
                if (flag <= getFactor(red, green, blue)) {
                    ring.setColor(new Color(red + 1, green, blue));
                } else {
                    ring.setColor(new Color(red - 1, green, blue));
                }
            } else if (color == 1) {
                // 如果是绿色
                // 离开颜色边界
                if (green == 0) {
                    ring.setColor(new Color(red, green + 1, blue));
                    return;
                }
                if (green == 255) {
                    ring.setColor(new Color(red, green - 1, blue));
                    return;
                }
                // 随机新近似的颜色
                if (flag <= getFactor(red, green, blue)) {
                    ring.setColor(new Color(red, green + 1, blue));
                } else {
                    ring.setColor(new Color(red, green - 1, blue));
                }
            } else {
                // 如果是蓝色
                // 离开颜色边界
                if (blue == 0) {
                    ring.setColor(new Color(red, green, blue + 1));
                    return;
                }
                if (blue == 255) {
                    ring.setColor(new Color(red, green, blue - 1));
                    return;
                }
                // 随机新近似的颜色
                if (flag <= getFactor(blue, green, red)) {
                    ring.setColor(new Color(red, green, blue + 1));
                } else {
                    ring.setColor(new Color(red, green, blue - 1));
                }
            }
        }

        /**
         * 根据主基色算出随机因子，该因子由其他两种基色的数值的差异决定
         *
         * @param main   主基色
         * @param other1 其他基色1
         * @param other2 其他基色2
         * @return
         */
        private int getFactor(int main, int other1, int other2) {
            double k = 0.007;
            double avg;
            int factor = (int) (200 * (other2 + 1.0) * (other1 + 1) / (Math.pow(other2 + 1, 2) + Math.pow(other1 + 1, 2)));
            avg = (other1 + other2) / 2.0;
            if (main < avg) {
                factor = (int) (-1 * k * factor + 50);
            } else {
                factor = (int) (k * factor + 50);
            }
            return factor;
        }

        /**
         * 集成画图----- 缺口环
         *
         * @param g
         */
        private void paintGraph(Graphics g) {
            ring1st.paint(g, this);
            ring2nd.paint(g, this);
            ring3rd.paint(g, this);

            ball1.paint(this, g);
            ball2.paint(this, g);
            ball3.paint(this, g);
            ball4.paint(this, g);
            ball5.paint(this, g);
        }

        /**
         * 自定义画缺口圆环的方法
         */
        private void myRingOfGap(int x, int y, int radius, int ringWidth, int gapStartAngle, int gapArcAngle, Graphics g,
                                 Color c) {
            // 基础环
            myRingPaint(x, y, radius, ringWidth, g, c);
            // 缺口扇形
            sectorPaintByCenter(x, y, radius, radius, gapStartAngle, gapArcAngle, g, backGround);
        }

        /**
         * 自定义画圆环的方法
         */
        private void myRingPaint(int x, int y, int radius, int ringWidth, Graphics g, Color c) {
            int smaller = radius - ringWidth;
            //外圈圆
            circlePaintByCenter(x, y, radius, radius, g, c);
            //背景圆
            circlePaintByCenter(x, y, smaller, smaller, g, backGround);
        }

        /**
         * 画圆方法，封装了原来的方法，使基准点落在圆心
         */
        private void circlePaintByCenter(int x, int y, int width, int height, Graphics g, Color c) {
            int ox, oy;
            ox = x - width / 2;
            oy = y - height / 2;
            if (c != null) {
                g.setColor(c);
                g.fillOval(ox, oy, width, height);
            } else {
                g.drawOval(ox, oy, width, height);
            }
        }

        /**
         * 画扇形的方法，封装了原来的方法，使基准点落在圆心
         */
        private void sectorPaintByCenter(int x, int y, int width, int height, int startAngle, int arcAngle,
                                         Graphics g, Color c) {
            int ox, oy;
            ox = x - width / 2;
            oy = y - height / 2;
            g.setColor(c);
            g.fillArc(ox, oy, width + 1, height + 1, startAngle, arcAngle);
        }

        /**
         * 写字符串的方法
         */
        private void myStringPaint(int x, int y, String str, int fontSize, Graphics g, Color c) {
            int ox, oy;
            double preXCoe = 2.1;// x 的修正系数，随字体发生改变
            double preYCoe = 1.0;// x 的修正系数，随字体发生改变
            g.setColor(c);
            g.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
            int len = str.length();
            ox = (int) (x - (fontSize * len) / (2 * preXCoe));
            oy = (int) (y + (fontSize) / (2 * preYCoe));

            g.drawString(str, ox, oy);
        }

        /**
         * 延时函数
         */
        private void MySleep(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 内部类：缺口环
         */
        class RingGap {

            private int baseX;
            private int baseY;
            private int radius;
            private int ringWidth;
            private int startAngle;
            private int arcAngle;
            private Color color;
            private int interval;
            private boolean direction;

            public RingGap(int baseX, int baseY, int radius, int ringWidth, int startAngle, int arcAngle, Color color) {
                this.baseX = baseX;
                this.baseY = baseY;
                this.radius = radius;
                this.ringWidth = ringWidth;
                this.startAngle = startAngle;
                this.arcAngle = arcAngle;
                this.color = color;
            }

            public void paint(Graphics g, MyPanel panel) {
                // 调用外部方法画环
                panel.myRingOfGap(baseX, baseY, radius, ringWidth, startAngle, arcAngle, g, color);
            }

            public int getStartAngle() {
                return startAngle;
            }

            public void setStartAngle(int startAngle) {
                this.startAngle = startAngle;
            }

            // 获取颜色
            public Color getColor() {
                return this.color;
            }

            // 设置颜色
            public void setColor(Color color) {
                this.color = color;
            }

            //getter 间隔
            public int getInterval() {
                return interval;
            }

            //setter
            public void setInterval(int interval) {
                this.interval = interval;
            }

            //getter 方向
            public boolean getDirection() {
                return direction;
            }

            //setter 方向
            public void setDirection(boolean direction) {
                this.direction = direction;
            }

        }

        /**
         * 内部类：绕行运动球
         */
        class RotateBall {

            private int baseX;  // 绕行基准点
            private int baseY;  // 绕行基准点
            private int angle;  // 相对于基准点的角度
            private int inteval;        //运行时间间隔
            private int rotateRadius;   //绕行半径
            private Color color;
            private int ballSize;

            /**
             * 构造器
             */
            public RotateBall(int x, int y, int angle, int rotateRadius, int ballSize, Color color) {
                baseX = x;
                baseY = y;
                this.angle = angle;
                this.rotateRadius = rotateRadius;
                this.color = color;
                this.ballSize = ballSize;
            }

            /**
             * 取球的角速度间隔，可以设计
             *
             * @param angle 参数是角度，意思是角速度与当前角度有关
             * @return
             */
            public int getInteval(int angle) {
                // TODO 设计球的运动模式
                /*
                 * 分为一个减速区和一个快速运动区
                 */
                if (angle >= 60 && angle <= 150) {
                    inteval = 12;
                } else {
                    inteval = 4;
                }
                return inteval;
            }

            /**
             * 绘制方法
             */
            public void paint(MyPanel panel, Graphics g) {
                // 获取当前坐标
                Location forNow = computeLocation();
                // 画圆
                panel.circlePaintByCenter(forNow.getX(), forNow.getY(), ballSize, ballSize, g, color);
            }

            /**
             * 计算球的位置坐标
             */
            private Location computeLocation() {
                int x, y;
                double radian = (angle / 180.0) * Math.PI;
                x = (int) (baseX + rotateRadius * Math.cos(radian));
                y = (int) (baseY - rotateRadius * Math.sin(radian));
                return new Location(x, y);
            }

            /**
             * 角度的 setter
             */
            void setAngle(int angle) {
                if (angle <= 0) {
                    this.angle = 360;
                } else {
                    this.angle = angle;
                }
            }

            /**
             * 角度的getter
             */
            int getAngle() {
                return angle;
            }

            /**
             * RotateBall 球的内部类，封装了球的圆心坐标
             */
            class Location {
                private int x;
                private int y;

                Location(int x, int y) {
                    this.x = x;
                    this.y = y;
                }

                int getX() {
                    return x;
                }

                int getY() {
                    return y;
                }
            }
        }
    }

    MyPanel getPanelInstance(int x, int y, Color c) {
        return new MyPanel(x, y, c);

    }
}