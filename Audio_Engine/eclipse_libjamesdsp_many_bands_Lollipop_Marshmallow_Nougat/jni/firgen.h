#pragma once
#include <math.h>
#define PI 3.14159265358979323846f
float BesselCal(float x)
{
	float Sum = 0.0, XtoIpower;
	int i, j, Factorial;
	for (i = 1; i<10; i++)
	{
		XtoIpower = powf(x / 2.0, (float)i);
		Factorial = 1;
		for (j = 1; j <= i; j++)Factorial *= j;
		Sum += powf(XtoIpower / (float)Factorial, 2.0);
	}
	return(1.0 + Sum);
}
void wKaiser(float w[], const int &N, float &Beta)
{
	if (Beta < 0.0f)Beta = 0.0f;
	if (Beta > 10.0f)Beta = 10.0f;
	int i, M, dM, TopWidth;
	float Arg;
	TopWidth = (int)(0 * (float)N);
	if (TopWidth % 2 != 0)TopWidth++;
	if (TopWidth > N)TopWidth = N;
	M = N - TopWidth;
	dM = M + 1;
	for (i = 0; i < M; i++) {
		Arg = Beta * sqrt(1.0 - powf(((float)(2.0f * (float)i + 2.0f) - dM) / dM, 2.0f));
		w[i] = BesselCal(Arg) / BesselCal(Beta);
	}
	return;
}
float sincCal(float x)
{
	if (x > -1.0E-5 && x < 1.0E-5)return(1.0);
	return(sinf(x) / x);
}
void wSinc(float w[], int &N, float &Beta)
{
	if (Beta < 0.0)Beta = 0.0;
	if (Beta > 10.0)Beta = 10.0;
	int i, M, dM, TopWidth;
	TopWidth = (int)(0 * (float)N);
	if (TopWidth % 2 != 0)TopWidth++;
	if (TopWidth > N)TopWidth = N;
	M = N - TopWidth;
	dM = M + 1;
	for (i = 0; i < M; i++) w[i] = sincCal((float)(2 * i + 1 - M) / dM * PI);
	for (i = 0; i < M; i++) w[i] = pow(w[i], Beta);
	return;
}
void wNone(float w[], const int &N)
{
	int i;
	for (i = 0; i < N; i++) {
		w[i] = 1.0f;
	}
	return;
}
void wBlackman(float w[], const int &N)
{
	int i;
	const float M = N - 1;
	for (i = 0; i < N; i++) {
		w[i] = 0.42f - (0.5f * cos(2.0f*PI*(float)i / M)) + (0.08f*cos(4.0f*PI*(float)i / M));
	}
	return;
}
void wHanning(float w[], const int &N)
{
	int i;
	const float M = N - 1;
	for (i = 0; i < N; i++) {
		w[i] = 0.5f * (1.0f - cos(2.0f*PI*(float)i / M));
	}
	return;
}
void wHamming(float w[], const int &N)
{
	int i;
	const float M = N - 1;
	for (i = 0; i < N; i++) {
		w[i] = 0.54f - (0.46f*cos(2.0f*PI*(float)i / M));
	}
	return;
}
void genSincFx(float sincfx[], const int &N, const float &fc)
{
	int i;
	const float M = N - 1;
	float n;
	for (i = 0; i < N; i++) {
		if (i == M / 2.0f) {
			sincfx[i] = 2.0f * fc;
		}
		else {
			n = (float)i - M / 2.0f;
			sincfx[i] = sinf(2.0f*PI*fc*n) / (PI*n);
		}
	}
	return;
}
void JfirLP(float h[], int &N, const int &WINDOW, const float &fc, float &Beta, float &gain)
{
	int i;
	float *w = new float[N];
	float *sincfx = new float[N];
	genSincFx(sincfx, N, fc);
	switch (WINDOW) {
	case 1:
		wNone(w, N);
		break;
	case 2:
		wBlackman(w, N);
		break;
	case 3:
		wHanning(w, N);
		break;
	case 4:
		wHamming(w, N);
		break;
	case 5:
		wKaiser(w, N, Beta);
		break;
	case 6:
		wSinc(w, N, Beta);
		break;
	default:
		break;
	}
	for (i = 0; i < N; i++) {
		h[i] = sincfx[i] * w[i] * gain;
	}
	delete[]w;
	delete[]sincfx;
	return;
}
void JfirHP(float h[], int &N, const int &WINDOW, const float &fc, float &Beta, float &gain)
{
	int i;
	if (N % 2 == 0)
	{
		N += 1;
	}
	JfirLP(h, N, WINDOW, fc, Beta, gain);
	for (i = 0; i < N; i++) {
		h[i] *= -1.0;
	}
	h[(N - 1) / 2] += 1.0;
	return;
}
void JfirBS(float h[], int &N, const int &WINDOW, const float &fc1, const float &fc2, float &Beta, float &gain)
{
	int i;
	if (N % 2 == 0)
	{
		N += 1;
	}
	float *h1 = new float[N];
	float *h2 = new float[N];
	JfirLP(h1, N, WINDOW, fc1, Beta, gain);
	JfirHP(h2, N, WINDOW, fc2, Beta, gain);
	for (i = 0; i < N; i++) {
		h[i] = h1[i] + h2[i];
	}
	delete[]h1;
	delete[]h2;
	return;
}
void JfirBP(float h[], int &N, const int &WINDOW, const float &fc1, const float &fc2, float &Beta, float &gain)
{
	int i;
	JfirBS(h, N, WINDOW, fc1, fc2, Beta, gain);
	for (i = 0; i < N; i++) {
		h[i] *= -1.0f;
	}
	h[(N - 1) / 2] += 1.0f;
	return;
}