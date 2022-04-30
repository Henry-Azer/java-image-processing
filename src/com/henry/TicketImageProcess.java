package com.henry;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TicketImageProcess extends JPanel {

    private BufferedImage rowTicketImg;
    private final BufferedImage resultTicketImage;
    static final int PIXELS_PER_POINT = 4;
    private final String BLANK_TICKET_IMG_URL = "https://i.ibb.co/wLrJ9BH/Requirements-08.png";

    public TicketImageProcess() {
        try {
            rowTicketImg = ImageIO.read(new URL(BLANK_TICKET_IMG_URL));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        resultTicketImage = process(rowTicketImg);
        System.out.println(resultTicketImage);
    }

    public static void generateTicketImage() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new TicketImageProcess());
        f.pack();
        f.setVisible(true);
    }

    private BufferedImage process(BufferedImage image) {
        String title = "Spider-Man far from home";
        Color titleColor = new Color(229, 137, 10);
        Font titleFont = new Font("Century Gothic Bold", Font.PLAIN, toPixels(9));

        Color subTitleColor = new Color(58, 58, 58);
        Font subTitleFont = new Font("Century Gothic Regular", Font.PLAIN, toPixels(6));


        insertTextOnImage(image, 80, 100, title, titleColor, titleFont);

        insertTextOnImage(image, 80, 270, "Date: ", subTitleColor, subTitleFont);
        insertTextOnImage(image, 150, 270, LocalDate.now().toString(), subTitleColor, subTitleFont);

        insertTextOnImage(image, 80, 320, "Time: ", subTitleColor, subTitleFont);
        String pattern = "h:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        insertTextOnImage(image, 150, 320, date, subTitleColor, subTitleFont);

        insertTextOnImage(image, 320, 270, "Hall & Seat: ", subTitleColor, subTitleFont);
        insertTextOnImage(image, 450, 270, "H1 - 42", subTitleColor, subTitleFont);

        insertTextOnImage(image, 320, 320, "Duration: ", subTitleColor, subTitleFont);
        insertTextOnImage(image, 450, 320, "130 Min ", subTitleColor, subTitleFont);


        return image;
    }

    private void insertTextOnImage(BufferedImage image, int xPosition, int yPosition, String text, Color fontColor, Font font) {
        BufferedImage tempImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.drawImage(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight(), this);
        graphics2D.setPaint(fontColor);
        graphics2D.setFont(font);

//        g2d.setFont(new Font("Arial", Font.BOLD, 35));

        graphics2D.drawString(text, xPosition, yPosition);
        graphics2D.dispose();
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    static int toPixels(int value) {
        return value * PIXELS_PER_POINT;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(rowTicketImg, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(rowTicketImg.getWidth(), rowTicketImg.getHeight());
    }

}