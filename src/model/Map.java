package model;

import model.block.Block;
import model.enemy.Enemy;
import model.hero.Bullet;
import model.hero.Hero;
import model.prize.Award;
import model.prize.X;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Map {

    private double remainingTime;
    private Hero hero;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Block> groundBlocks = new ArrayList<>();
    private ArrayList<Award> awards = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Endpoint endPoint;
    private BufferedImage backgroundImage;
    private double bottomBorder = 720 - 96;
    private String path;


    public Map(double remainingTime, BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.remainingTime = remainingTime;
    }


    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Award> getAwards() {
        return awards;
    }

    public ArrayList<Block> getAllBlocks() {
        ArrayList<Block> allBricks = new ArrayList<>();

        allBricks.addAll(blocks);
        allBricks.addAll(groundBlocks);

        return allBricks;
    }

    public void addBrick(Block block) {
        this.blocks.add(block);
    }

    public void addGroundBrick(Block block) {
        this.groundBlocks.add(block);
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void drawMap(Graphics2D g2) {
        drawBackground(g2);
        drawAwards(g2);
        drawBlocks(g2);
        drawEnemies(g2);
        drawBullets(g2);
        drawHero(g2);
        endPoint.draw(g2);
    }

    private void drawBullets(Graphics2D g2) {
        for (Bullet bullet : bullets) {
            bullet.draw(g2);
        }
    }

    private void drawAwards(Graphics2D g2) {
        for (Award score : awards) {
            if (score instanceof X) {
                ((X) score).draw(g2);
            }
            //kann weitere gift hier erzeugt werden
        }
    }

    private void drawBackground(Graphics2D g2) {
        g2.drawImage(backgroundImage, 0, 0, null);
    }

    private void drawBlocks(Graphics2D g2) {
        for (Block block : blocks) {
            if (block != null)
                block.draw(g2);
        }

        for (Block block : groundBlocks) {
            block.draw(g2);
        }
    }

    private void drawEnemies(Graphics2D g2) {
        for (Enemy enemy : enemies) {
            if (enemy != null)
                enemy.draw(g2);
        }
    }

    private void drawHero(Graphics2D g2) {
        hero.draw(g2);
    }

    public void updateLocations() {
        hero.updateLocation();
        for (Enemy enemy : enemies) {
            enemy.updateLocation();
        }

        for (Iterator<Award> scoreIterator = awards.iterator(); scoreIterator.hasNext(); ) {
            Award score = scoreIterator.next();
            if (score instanceof X) {
                ((X) score).updateLocation();
                if (((X) score).getRevealBoundary() > ((X) score).getY()) {
                    scoreIterator.remove();
                }
            }
        }

        for (Bullet bullet : bullets) {
            bullet.updateLocation();
        }

        endPoint.updateLocation();
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public void addXScore(Award score) {
        awards.add(score);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void setEndPoint(Endpoint endPoint) {
        this.endPoint = endPoint;
    }

    public Endpoint getEndPoint() {
        return endPoint;
    }

    public void removeBullet(Bullet object) {
        bullets.remove(object);
    }

    public void removeEnemy(Enemy object) {
        enemies.remove(object);
    }

    public void removeAward(Award object) {
        awards.remove(object);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void updateTime(double passed) {
        remainingTime = remainingTime - passed;
    }

    public boolean isTimeOver() {
        return remainingTime <= 0;
    }

    public double getRemainingTime() {
        return remainingTime;
    }
}
