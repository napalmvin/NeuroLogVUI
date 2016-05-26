# -*- coding: utf-8 -*-
# Generated by Django 1.9.4 on 2016-03-27 18:32
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Doctors',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('first_name', models.CharField(max_length=30)),
                ('last_name', models.CharField(max_length=45)),
                ('birth_date', models.DateField(auto_now_add=True)),
                ('sex', models.SmallIntegerField(choices=[(0, 'Male'), (1, 'Female')])),
                ('race', models.SmallIntegerField(choices=[(0, 'Caucasian'), (1, 'African'), (2, 'Mongoloid')])),
                ('photo', models.BinaryField(null=True)),
                ('qualiffication', models.CharField(max_length=30)),
            ],
        ),
        migrations.CreateModel(
            name='Patients',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('first_name', models.CharField(max_length=30)),
                ('last_name', models.CharField(max_length=45)),
                ('birth_date', models.DateField(auto_now_add=True)),
                ('sex', models.SmallIntegerField(choices=[(0, 'Male'), (1, 'Female')])),
                ('race', models.SmallIntegerField(choices=[(0, 'Caucasian'), (1, 'African'), (2, 'Mongoloid')])),
                ('photo', models.BinaryField(null=True)),
                ('prerequisite', models.CharField(max_length=125)),
            ],
        ),
    ]